package xyz.kewiany.menusy.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import xyz.kewiany.menusy.ui.MainViewModel.Event


@Composable
fun BottomBar(
    bottomBarState: MutableState<Boolean>,
    eventHandler: (Event) -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(0) }

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(elevation = 10.dp) {
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = Icons.Default.Home, "")
                    },
                    label = { Text(text = "Menu") },
                    selected = (selectedIndex.value == 0),
                    onClick = {
                        eventHandler(Event.MenuClicked)
                        selectedIndex.value = 0
                    })

                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = Icons.Default.Add, "")
                    },
                    label = { Text(text = "Order") },
                    selected = (selectedIndex.value == 1),
                    onClick = {
                        eventHandler(Event.OrderClicked)
                        selectedIndex.value = 1
                    })
            }
        }
    )
}
