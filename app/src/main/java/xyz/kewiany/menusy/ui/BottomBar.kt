package xyz.kewiany.menusy.presentation.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import xyz.kewiany.menusy.MainViewModel
import xyz.kewiany.menusy.MainViewModel.Event


@Composable
fun BottomBar(
    state: State<MainViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(0) }

    AnimatedVisibility(
        visible = state.value.showBottomBar,
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
                        val orderedProductsCount = state.value.orderedProductsCount
                        if (orderedProductsCount > 0) {
                            BadgedBox(badge = { Badge { Text(orderedProductsCount.toString()) } }) {
                                Icon(imageVector = Icons.Filled.Add, "")
                            }
                        } else {
                            Icon(imageVector = Icons.Filled.Add, "")
                        }
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