package com.example.foodrecipeapplicaiton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.api.service.RetrofitClient
import com.example.foodrecipeapplicaiton.repository.RecipeRepository
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColor
import com.example.foodrecipeapplicaiton.ui.theme.TopAppBarColorDark
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColor
import com.example.foodrecipeapplicaiton.ui.theme.TopBarTextColorDark
import com.example.foodrecipeapplicaiton.view.components.BottomBar
import com.example.foodrecipeapplicaiton.view.components.BottomNavItem
import com.example.foodrecipeapplicaiton.view.navigation.AppNavHost
import com.example.foodrecipeapplicaiton.view.routes.Routes
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { authResult ->
                        if (authResult.isSuccessful) {
                            setContent {
                                FoodRecipeApplicaitonTheme(darkTheme = isSystemInDarkTheme()) {
                                    val bottomNavItems = listOf(
                                        BottomNavItem(Routes.MAIN, Icons.Filled.Home, "Home", "main/{category}"),
                                        BottomNavItem(Routes.FAVORITE_SCREEN, Icons.Filled.Favorite, "Favorite", "favorites"),
                                        BottomNavItem(Routes.MAIN, Icons.Filled.Notifications, "Notifications", "main/{category}"),
                                        BottomNavItem(Routes.MAIN, Icons.Filled.Person2, "Profile", "main/{category}")
                                    )

                                    val recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(
                                        RecipeRepository(RetrofitClient.recipeApiService)
                                    ))

                                    MainContent(startDestination = Routes.MAIN, recipeViewModel = recipeViewModel, bottomNavItems = bottomNavItems)
                                }
                            }
                        } else {
                            // Handle sign-in error
                        }
                    }
            }
        }

        val startDestination = if (auth.currentUser != null) Routes.MAIN else Routes.LOGIN

        setContent {
            val recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(
                RecipeRepository(RetrofitClient.recipeApiService)
            ))

            val bottomNavItems = listOf(
                BottomNavItem(Routes.MAIN, Icons.Filled.Home, "Home", "main/{category}"),
                BottomNavItem(Routes.FAVORITE_SCREEN, Icons.Filled.Favorite, "Favorite", "favorites"),
                BottomNavItem(Routes.MAIN, Icons.Filled.Notifications, "Notifications", "main/{category}"),
                BottomNavItem(Routes.MAIN, Icons.Filled.Person2, "Profile", "main/{category}")
            )

            FoodRecipeApplicaitonTheme(darkTheme = isSystemInDarkTheme()) {
                MainContent(startDestination = startDestination, recipeViewModel = recipeViewModel, bottomNavItems = bottomNavItems)
            }
        }
    }
}


@Composable
private fun MainContent(startDestination: String, recipeViewModel: RecipeViewModel, bottomNavItems: List<BottomNavItem>) {
    val darkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = if (darkTheme) TopAppBarColorDark else TopAppBarColor)
                        .height(60.dp)
                        .padding(horizontal = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "˗ˏˋ ★ ˎˊ˗",
                        fontSize = 20.sp,
                        color = if (darkTheme) TopBarTextColorDark else TopBarTextColor
                    )
                }
            },
            bottomBar = {
                if (currentRoute != Routes.LOGIN && currentRoute != Routes.SIGN_UP) {
                    BottomBar(navController = navController, bottomNavItems = bottomNavItems, onItemClick = { screenRoute ->
                        navController.navigate(screenRoute)
                    })
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavHost(navController = navController, recipeViewModel = recipeViewModel)
            }
        }
    }
}
