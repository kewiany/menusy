package xyz.kewiany.menusy.feature.menu

import xyz.kewiany.menusy.common.CategoryTab
import xyz.kewiany.menusy.common.UiItem

data class MenuContentData(
    val tabs: List<CategoryTab>,
    val items: List<UiItem>
)