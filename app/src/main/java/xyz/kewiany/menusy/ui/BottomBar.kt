package xyz.kewiany.menusy.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.utils.Navigation.Destination.WELCOME_PATH


@Composable
fun BottomBar(
    navController: NavHostController = rememberNavController()
) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = { Text(text = "Menu") },
            selected = (selectedIndex.value == 0),
            onClick = {
                navController.navigate(WELCOME_PATH)
                selectedIndex.value = 0
            })

        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Add, "")
            },
            label = { Text(text = "Order") },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
            })
    }
}
