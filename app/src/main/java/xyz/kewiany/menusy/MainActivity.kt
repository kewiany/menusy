package xyz.kewiany.menusy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App(navigator)
            }
        }
    }
}