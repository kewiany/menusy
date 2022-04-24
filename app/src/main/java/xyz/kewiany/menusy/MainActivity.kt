package xyz.kewiany.menusy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import xyz.kewiany.menusy.ui.theme.AppTheme
import xyz.kewiany.menusy.ui.utils.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navigator = Navigator()
        setContent {
            AppTheme {
                App(navigator)
            }
        }
    }
}