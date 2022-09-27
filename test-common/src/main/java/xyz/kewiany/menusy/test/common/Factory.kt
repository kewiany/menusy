package xyz.kewiany.menusy.test.common

import xyz.kewiany.menusy.common.CategoryTab
import xyz.kewiany.menusy.model.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.random.Random

fun createText(): String = Random.nextLong().toString()

fun createProduct(
    id: String = "id",
    name: String = "name",
    description: String = "description",
    price: BigDecimal = BigDecimal.valueOf(0.0),
    menuId: String = "menuId",
    categoryId: String = "categoryId",
) = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    menuId = menuId,
    categoryId = categoryId,
)

fun createHistoryOrder(
    orderId: Long = Random.nextLong(),
    date: LocalDateTime = LocalDateTime.parse("2019-03-20T15:58:00"),
    totalPrice: BigDecimal = BigDecimal.valueOf(0.0),
    totalQuantity: Int = 1,
    products: List<HistoryProduct> = listOf(
        createHistoryProduct(),
        createHistoryProduct()
    ),
    placeName: String = "placeName",
    placeAddress: String = "placeAddress"
) = HistoryOrder(
    orderId = orderId,
    date = date,
    totalPrice = totalPrice,
    totalQuantity = totalQuantity,
    products = products,
    placeName = placeName,
    placeAddress = placeAddress
)

fun createHistoryProduct(
    productId: Long = Random.nextLong(),
    name: String = "name",
    description: String = "description",
    price: BigDecimal = BigDecimal.valueOf(0.0),
    quantity: Int = 1
) = HistoryProduct(
    productId,
    name,
    description,
    price,
    quantity
)

fun createOrderedProduct(
    quantity: Int = 0,
    product: Product = createProduct()
) = OrderedProduct(
    quantity = quantity,
    product = product
)

fun createPlace(
    id: String = "id",
    name: String = "name",
    address: String = "address"
) = Place(
    id = id,
    name = name,
    address = address
)

fun createMenu(
    id: String = "id",
    name: String = "name",
    categories: List<Category> = listOf(createCategory(), createCategory())
) = Menu(
    id = id,
    name = name,
    categories = categories
)

fun createCategory(
    id: String = "id",
    name: String = "name"
) = Category(
    id = id,
    name = name
)

fun createCategoryTab(
    id: String = "id",
    name: String = "name"
) = CategoryTab(
    id = id,
    name = name
)

//fun createCategoryUIItem(
//    category: Category = createCategory()
//) = category.asUIItem()
//
//fun createProductUIItem(
//    product: Product = createProduct(),
//    quantity: Int = 0
//) = product.asUIItem(quantity)
//
//fun createContent(
//    tabs: List<xyz.kewiany.menusy.feature.menu.items.CategoryTab> = listOf(createCategoryTab(), createCategoryTab()),
//    items: List<UiItem> = listOf(createProductUIItem(), createProductUIItem())
//) = Content(
//    tabs = tabs,
//    items = items
//)