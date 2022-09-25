package xyz.kewiany.menusy.feature.menu.items.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.CategoryUiItem
import xyz.kewiany.menusy.android.common.ProductUiItem
import xyz.kewiany.menusy.android.common.ui.ErrorDialog
import xyz.kewiany.menusy.android.common.ui.ProductItem
import xyz.kewiany.menusy.android.common.ui.ProgressBar
import xyz.kewiany.menusy.common.CategoryTab
import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel.Event

@Composable
fun MenuItemsScreen(
    menuId: String,
    state: State<MenuItemsViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventHandler(Event.TriggerLoadMenu)
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val tabs = state.value.tabs
        if (tabs.isNotEmpty()) {
            CategoriesScrollableTabRow(
                tabs = state.value.tabs,
                selectedTabIndex = state.value.currentTab
            ) { id -> eventHandler(Event.TabClicked(id)) }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
                state = listState
            ) {
                coroutineScope.launch {
                    listState.animateScrollToItem(state.value.currentCategory)
                }
                items(state.value.items) { item ->
                    when (item) {
                        is CategoryUiItem -> {
                            CategoryItem(
                                name = item.name
                            )
                        }
                        is ProductUiItem -> {
                            val id = item.id
                            ProductItem(
                                name = item.name,
                                description = item.description,
                                price = item.price,
                                ordered = item.ordered,
                                quantity = item.quantity,
                                onItemClicked = { eventHandler(Event.ProductClicked(id)) },
                                onDecreaseQuantityClicked = { eventHandler(Event.DecreaseQuantityClicked(id)) },
                                onIncreaseQuantityClicked = { eventHandler(Event.IncreaseQuantityClicked(id)) }
                            )
                        }
                    }
                }
            }
            if (state.value.showLoading) {
                ProgressBar()
            }
        }
        if (state.value.showError) {
            ErrorDialog(
                onDismissRequest = { eventHandler(Event.TriggerDismissError) },
                onOKClicked = { eventHandler(Event.ErrorOKClicked) }
            )
        }
    }
}


@Composable
private fun CategoriesScrollableTabRow(
    tabs: List<CategoryTab>,
    selectedTabIndex: Int,
    onTabClick: (String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        edgePadding = 0.dp,
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Tab(
                selected = selectedTabIndex == tabIndex,
                onClick = { onTabClick(tab.id) },
                text = { Text(text = tab.name) }
            )
        }
    }
}