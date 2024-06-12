import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.room.MainDao
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel

class RecipeViewModelFactory(private val repository: RecipeRepository, private val mainDao: MainDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repository, mainDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
