package xyz.kewiany.menusy.ui.welcome

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun WelcomeScreen(
    isLoading: State<Boolean>
) {
    Text("Welcome ${isLoading.value}")
}