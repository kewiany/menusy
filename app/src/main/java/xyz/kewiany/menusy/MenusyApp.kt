package xyz.kewiany.menusy

import androidx.compose.runtime.Composable
import xyz.kewiany.menusy.ui.utils.MenusyNavGraph
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun MenusyApp() {
    val welcomeViewModel = WelcomeViewModel()
    MenusyNavGraph(
        welcomeViewModel = welcomeViewModel
    )
}