package com.example.myapplication.util.navigation

import com.example.myapplication.R

interface NavDestination {
    val route: String
    val icon: Int
    val title: String
}

interface Destination{
    val route: String
}

object Home: NavDestination{
    override val route = "Home"
    override val icon = R.drawable.baseline_newspaper_24
    override val title = "Home"
}

object Saved: NavDestination{
    override val route = "Saved"
    override val icon = R.drawable.baseline_bookmark_24
    override val title = "Saved"
}

object Article: Destination{
    override val route = "Article"
}