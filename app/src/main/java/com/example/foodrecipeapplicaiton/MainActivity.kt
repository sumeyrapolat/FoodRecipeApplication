package com.example.foodrecipeapplicaiton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipeapplicaiton.ui.theme.FoodRecipeApplicaitonTheme
import com.example.foodrecipeapplicaiton.viewmodel.RecipeViewModel
import com.example.foodrecipeapplicaiton.room.FavoriteRecipeDao
import com.example.foodrecipeapplicaiton.ui.view.components.BottomBar
import com.example.foodrecipeapplicaiton.ui.view.components.BottomNavItem
import com.example.foodrecipeapplicaiton.ui.view.components.SideDrawerContent
import com.example.foodrecipeapplicaiton.ui.view.components.TopBar
import com.example.foodrecipeapplicaiton.ui.view.navigation.AppNavHost
import com.example.foodrecipeapplicaiton.ui.view.routes.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var favoriteRecipeDao: FavoriteRecipeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)  // Adjust window insets for Compose

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
                val recipeViewModel: RecipeViewModel = hiltViewModel()
                MainContentWithDrawer(recipeViewModel = recipeViewModel, favoriteRecipeDao = favoriteRecipeDao, startDestination = startDestination)
            }
        }
    }
}

@Composable
private fun MainContentWithDrawer(recipeViewModel: RecipeViewModel, favoriteRecipeDao: FavoriteRecipeDao, startDestination: String) {
    val darkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val onItemClick: (String) -> Unit = { route ->
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
    val uriState = remember { MutableStateFlow("") }

    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            uriState.value = uri.toString()
        }
    }

    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    var userName by remember { mutableStateOf("User") }
    var userEmail by remember { mutableStateOf("user@example.com") }

    val userId = auth.currentUser?.uid
    if (userId != null) {
        LaunchedEffect(userId) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("username") ?: "User"
                        userEmail = document.getString("email") ?: "user@example.com"
                    }
                }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                SideDrawerContent(
                    userName = userName,
                    userEmail = userEmail,
                    onLogout = {
                        auth.signOut()
                        navController.navigate(Routes.LOGIN)
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopBar(darkTheme = darkTheme, onMenuClick = { coroutineScope.launch { drawerState.open() } })
                },
                bottomBar = {
                    if (currentRoute != Routes.LOGIN && currentRoute != Routes.SIGN_UP) {
                        val bottomNavItems = listOf(
                            BottomNavItem(Routes.MAIN, Icons.Filled.Home, "main/{category}", onClick = { onItemClick(Routes.MAIN) }),
                            BottomNavItem(Routes.FAVORITE_SCREEN, Icons.Filled.Favorite, "favorites", onClick = { onItemClick(Routes.FAVORITE_SCREEN) }),
                            BottomNavItem(Routes.CHAT_SCREEN, Icons.Filled.QuestionAnswer, "chat_screen", onClick = { onItemClick(Routes.CHAT_SCREEN) }),
                            BottomNavItem(Routes.MAIN, Icons.Filled.Notifications, "main/{category}", onClick = { onItemClick(Routes.MAIN) })
                        )

                        BottomBar(navController = navController, bottomNavItems = bottomNavItems, onItemClick = onItemClick)
                    }
                }
            ) { contentPadding ->
                Box(
                    modifier = Modifier
                        .padding(top = contentPadding.calculateTopPadding(), bottom = contentPadding.calculateBottomPadding())
                        .fillMaxSize()
                ) {
                    AppNavHost(
                        navController = navController,
                        recipeViewModel = recipeViewModel,
                        favoriteRecipeDao = favoriteRecipeDao,
                        imagePicker = imagePicker,
                        paddingValues = contentPadding,
                        uriState = uriState
                    )
                }
            }
        }
    }
}