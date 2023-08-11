package com.example.myapplication.util.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.database.SavedArticleDB
import com.example.myapplication.ui.article_screen.ArticleScreen
import com.example.myapplication.ui.home_screen.HomeScreen
import com.example.myapplication.ui.saved_screen.SavedScreen

@Composable
fun Navigation(database: SavedArticleDB){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ){
            NavHost(navController = navController, startDestination = Home.route){
                composable(Home.route){
                    HomeScreen(navController, database)
                }
                composable(Saved.route){
                    SavedScreen(navController, database)
                }
                composable(
                    route = Article.route + "/{articleURL}",
                    arguments = listOf(
                        navArgument("articleURL"){
                            type = NavType.StringType
                        }
                    )
                ){entry ->
                    ArticleScreen(articleURL = entry.arguments?.getString("articleURL").toString())
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController){
    val destinationList = listOf(
        Home,
        Saved
    )
    val selectedIndex = rememberSaveable{
        mutableStateOf(0)
    }
    BottomNavigation{
        destinationList.forEachIndexed { index, destination ->
            BottomNavigationItem(
                label = {
                        Text(text = destination.title)
                },
                icon = {
                       Icon(
                           painter = painterResource(id = destination.icon),
                           contentDescription = destination.title
                       )
                },
                selected = index == selectedIndex.value,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(destination.route){
                        popUpTo(Home.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}