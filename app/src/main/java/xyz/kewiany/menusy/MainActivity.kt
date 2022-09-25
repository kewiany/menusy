package xyz.kewiany.menusy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import xyz.kewiany.menusy.android.common.navigation.Navigator
import xyz.kewiany.menusy.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun menuItemsViewModelFactory(): xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel.Factory
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App(navigator)
            }
        }
    }
}