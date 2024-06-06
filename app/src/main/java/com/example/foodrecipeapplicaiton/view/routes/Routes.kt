package com.example.foodrecipeapplicaiton.view.routes


object Routes {
    const val SIGN_UP = "signUp"
    const val LOGIN = "login"
    const val MAIN = "main/{category}"

    fun mainRoute(category: String): String = "main/$category"
}