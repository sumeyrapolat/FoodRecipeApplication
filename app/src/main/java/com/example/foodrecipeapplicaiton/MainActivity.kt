package com.example.foodrecipeapplicaiton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.R
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
import com.example.foodrecipeapplicaiton.view.screens.ProfileCard
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

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

        val startDestination = Routes.MAIN

        setContent {
            FoodRecipeApplicaitonTheme(darkTheme = isSystemInDarkTheme()) {
                val recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(
                    RecipeRepository(RetrofitClient.recipeApiService)
                ))
                MainContent(recipeViewModel = recipeViewModel, startDestination = startDestination)
            }
        }
    }
}

@Composable
private fun MainContent(recipeViewModel: RecipeViewModel, startDestination: String) {
    val darkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val onItemClick: (String) -> Unit = { route ->
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
    val uriState = remember { MutableStateFlow("") }

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                uriState.value = uri.toString()
            }
        }

    val paddingValues = PaddingValues(
        top = if (currentRoute == Routes.LOGIN || currentRoute == Routes.SIGN_UP) 0.dp else 16.dp,
        bottom = 0.dp,
        start = 0.dp,
        end = 0.dp
    )

    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    var userName by remember { mutableStateOf("User") }

    val userId = auth.currentUser?.uid
    if (userId != null) {
        LaunchedEffect(userId) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("username") ?: "User"
                    }
                }
        }
    }

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
                    val bottomNavItems = listOf(
                        BottomNavItem(Routes.MAIN, Icons.Filled.Home, "main/{category}", onClick = { onItemClick(Routes.MAIN) }),
                        BottomNavItem(Routes.FAVORITE_SCREEN, Icons.Filled.Favorite, "favorites", onClick = { onItemClick(Routes.FAVORITE_SCREEN) }),
                        BottomNavItem(Routes.CHAT_SCREEN, Icons.Filled.QuestionAnswer, "chat_screen", onClick = { onItemClick(Routes.CHAT_SCREEN) }),
                        BottomNavItem(Routes.MAIN, Icons.Filled.Notifications, "main/{category}", onClick = { onItemClick(Routes.MAIN) }),
                        BottomNavItem(Routes.PROFILE_SCREEN, Icons.Filled.Person2, "main/{category}", onClick = { onItemClick(Routes.PROFILE_SCREEN) })
                    )

                    BottomBar(navController = navController, bottomNavItems = bottomNavItems, onItemClick = onItemClick)
                }
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier.padding(contentPadding)
            ) {
                AppNavHost(navController = navController, recipeViewModel = recipeViewModel, imagePicker = imagePicker, paddingValues = paddingValues, uriState = uriState, mainActivity = MainActivity())

                if (currentRoute == Routes.PROFILE_SCREEN) {
                    ProfileCard(
                        userEmail = auth.currentUser!!.email.toString(),
                        username = userName,
                        onLogout = {
                            auth.signOut()
                            navController.navigate(Routes.LOGIN)
                        }
                    )
                }
            }
        }
    }
}
