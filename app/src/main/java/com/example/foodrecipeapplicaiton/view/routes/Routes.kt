package com.example.foodrecipeapplicaiton.view.routes


object Routes {

    const val SIGN_UP = "signUp"
    const val LOGIN = "login"
    const val MAIN = "main/{category}"
    const val DETAIL_SCREEN = "detail_screen"
    const val FAVORITE_SCREEN = "favorites"

    fun mainRoute(category: String): String = "main/$category"

    fun detailScreenRoute(recipeId: Int): String = "$DETAIL_SCREEN/$recipeId"
}
