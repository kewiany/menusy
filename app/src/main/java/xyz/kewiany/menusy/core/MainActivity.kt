package xyz.kewiany.menusy.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.common.theme.AppTheme
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun menuItemsViewModelFactory(): MenuItemsViewModel.Factory
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App(navigator)
            }
        }
    }
}