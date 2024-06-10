package com.example.foodrecipeapplicaiton.view.routes


object Routes {

    const val SIGN_UP = "signUp"
    const val LOGIN = "login"
    const val MAIN = "main/{category}"
    const val DETAIL_SCREEN = "detail_screen"
    const val FAVORITE_SCREEN = "favorites"
    const val CHAT_SCREEN = "chat_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val FAVORITE_DETAIL_SCREEN = "favorite_detail_screen"


    fun mainRoute(category: String): String = "main/$category"

    fun detailScreenRoute(recipeId: Int): String = "$DETAIL_SCREEN/$recipeId"

    fun favoriteDetailScreenRoute(recipeId: Int): String = "$FAVORITE_DETAIL_SCREEN/$recipeId"}