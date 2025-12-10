package com.shevelev.mywinningstreaks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shevelev.mywinningstreaks.screens.main.ui.ScreenMainRoot
import com.shevelev.mywinningstreaks.screens.settings.ui.ScreenSettingsRoot
import com.shevelev.mywinningstreaks.shared.navigation.Routes
import com.shevelev.mywinningstreaks.shared.ui.theme.MyWinningStreaksTheme

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    MyWinningStreaksTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.Main.name,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(route = Routes.Main.name) {
                ScreenMainRoot()
            }
            composable(route = Routes.Settings.name) {
                ScreenSettingsRoot()
            }
        }
    }
}