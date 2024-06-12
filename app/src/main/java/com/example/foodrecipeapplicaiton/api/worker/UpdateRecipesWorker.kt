import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.foodrecipeapplicaiton.R
import com.example.foodrecipeapplicaiton.api.key.Constants.API_KEY
import com.example.foodrecipeapplicaiton.api.service.RecipeApiService
import com.example.foodrecipeapplicaiton.room.MainDatabase
import com.example.foodrecipeapplicaiton.room.MainEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateRecipesWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("UpdateRecipesWorker", "Work started")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RecipeApiService::class.java)

        return try {
            val response = api.getRandomRecipes(API_KEY, 10)
            if (response.recipes.isNotEmpty()) {
                Log.d("UpdateRecipesWorker", "Recipes fetched successfully")

                val db = MainDatabase.getDatabase(applicationContext)
                val recipes = response.recipes.map { recipe ->
                    MainEntity(
                        id = recipe.id,
                        title = recipe.title,
                        image = recipe.image,
                        veryPopular = recipe.veryPopular,
                        veryHealthy = recipe.veryHealthy,
                        vegetarian = recipe.vegetarian,
                        glutenFree = recipe.glutenFree,
                        vegan = recipe.vegan,
                        dairyFree = recipe.dairyFree,
                        extendedIngredients = recipe.extendedIngredients.toString(),
                        instructions = recipe.instructions,
                        servings = recipe.servings,
                        readyInMinutes = recipe.readyInMinutes
                    )
                }
                db.mainDao().insertRecipes(recipes)
                Log.d("UpdateRecipesWorker", "Recipes inserted into database")

                // Bildirim gönder
                sendNotification("New Recipes Available", "Check out the latest recipes!")

                Result.success()
            } else {
                Log.d("UpdateRecipesWorker", "No recipes fetched, retrying")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e("UpdateRecipesWorker", "Error fetching recipes", e)
            Result.failure()
        }
    }

    private fun sendNotification(title: String, message: String) {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val builder = NotificationCompat.Builder(applicationContext, "RECIPE_UPDATE_CHANNEL")
                .setSmallIcon(R.drawable.darknoimage) // Bildirim simgesini ayarlayın
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(1, builder.build())
            }
        } else {
            Log.e("UpdateRecipesWorker", "Notification permission not granted")
        }
    }
}
