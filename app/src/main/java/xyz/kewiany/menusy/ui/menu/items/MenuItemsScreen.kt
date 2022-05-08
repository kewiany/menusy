package xyz.kewiany.menusy.ui.menu.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event

@Composable
fun MenuItemsScreen(
    menuId: String,
    state: State<MenuItemsViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            val tabs = state.value.tabs
            if (tabs.isNotEmpty()) {
                CategoriesScrollableTabRow(
                    tabs = state.value.tabs,
                    selectedTabIndex = state.value.currentTab
                ) { index -> eventHandler(Event.TabClicked(index)) }
            }
            Text(text = "menu id $menuId")
            val items = state.value.items
            items.forEach { item ->
                when (item) {
                    is CategoryUiItem -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Blue)
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item.id,
                            )
                        }
                    }
                    is ProductUiItem -> {
                        Column(
                            modifier = Modifier
                                .clickable { eventHandler(Event.ProductClicked(item.id)) }
                                .background(Color.Green)
                                .padding(10.dp)
                        ) {
                            Row {
                                Text(
                                    text = item.id,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Text(
                                    text = item.name,
                                    modifier = Modifier.weight(0.6f)
                                )
                                Text(
                                    text = item.price,
                                    modifier = Modifier.weight(0.2f)
                                )
                            }
                            Row {
                                Text(
                                    text = item.description
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoriesScrollableTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        edgePadding = 0.dp,
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Tab(
                selected = selectedTabIndex == tabIndex,
                onClick = { onTabClick(tabIndex) },
                text = { Text(text = tab) }
            )
        }
    }
}