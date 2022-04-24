package xyz.kewiany.menusy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App(navigator)
            }
        }
    }
}