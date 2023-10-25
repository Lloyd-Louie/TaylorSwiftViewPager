package com.example.myapplicationwrq.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AlbumScreen : Screen("album_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg") }
        }
    }
}