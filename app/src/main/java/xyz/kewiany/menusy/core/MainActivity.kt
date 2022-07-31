package xyz.kewiany.menusy.core

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.common.theme.AppTheme
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator
    private var contentHasLoaded: Boolean = false

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun menuItemsViewModelFactory(): MenuItemsViewModel.Factory
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App(navigator)
            }
        }
        startContentLoading()
        setUpSplashScreen(splashScreen)
    }

    private fun startContentLoading() {
        lifecycleScope.launch {
            delay(1000)
            contentHasLoaded = true
        }
    }

    private fun setUpSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (contentHasLoaded) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else false
            }
        })
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.view.width.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 100L
                doOnEnd { splashScreenView.remove() }
            }

            slideBack.start()
        }
    }
}