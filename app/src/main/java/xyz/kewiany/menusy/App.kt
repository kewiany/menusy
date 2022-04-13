package xyz.kewiany.menusy

import androidx.compose.runtime.Composable
import xyz.kewiany.menusy.ui.utils.NavGraph
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun App() {
    val welcomeViewModel = WelcomeViewModel()
    NavGraph(
        welcomeViewModel = welcomeViewModel
    )
}