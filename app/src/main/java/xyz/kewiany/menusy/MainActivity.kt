package xyz.kewiany.menusy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import xyz.kewiany.menusy.ui.theme.MenusyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenusyTheme {
                MenusyApp()
            }
        }
    }
}