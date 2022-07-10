package xyz.kewiany.menusy.ui.menu.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event

@Composable
fun MenuItemsScreen(
    menuId: String,
    state: State<MenuItemsViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            val tabs = state.value.tabs
            if (tabs.isNotEmpty()) {
                CategoriesScrollableTabRow(
                    tabs = state.value.tabs,
                    selectedTabIndex = state.value.currentTab
                ) { index -> eventHandler(Event.TabClicked(index)) }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
                state = listState
            ) {
                coroutineScope.launch {
                    listState.animateScrollToItem(state.value.currentCategory)
                }
                item {
                    Text(text = "menu id $menuId")
                }
                items(state.value.items) { item ->
                    when (item) {
                        is CategoryUiItem -> CategoryItem(item.id)
                        is ProductUiItem -> {
                            ProductItem(
                                item.id,
                                item.name,
                                item.description,
                                item.price,
                                item.quantity,
                                { id -> eventHandler(Event.DecreaseQuantityClicked(id)) },
                                { id -> eventHandler(Event.IncreaseQuantityClicked(id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    id: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = id,
        )
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