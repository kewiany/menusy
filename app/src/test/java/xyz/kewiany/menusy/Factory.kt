import xyz.kewiany.menusy.domain.model.HistoryOrder
import xyz.kewiany.menusy.domain.model.HistoryProduct
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import kotlin.random.Random

//package xyz.kewiany.menusy
//
//import xyz.kewiany.menusy.domain.model.Category
//import xyz.kewiany.menusy.domain.model.Menu
//import xyz.kewiany.menusy.domain.model.Product
//
//fun createCategory(
//    id: String = "id",
//    name: String = "name"
//) = Category(
//    id = id,
//    name = name
//)
//
//fun createMenu(
//    id: String = "id",
//    name: String = "name",
//    categories: List<Category> = listOf(
//        createCategory(),
//        createCategory()
//    )
//) = Menu(
//    id = id,
//    name = name,
//    categories = categories
//)
//

fun createText(): String = Random.nextLong().toString()

fun createProduct(
    id: String = "id",
    name: String = "name",
    description: String = "description",
    price: Float = 0f,
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
    date: String = "date",
    totalPrice: Float = 10.0f,
    totalQuantity: Int = 1,
    products: List<HistoryProduct> = listOf(
        createHistoryProduct(),
        createHistoryProduct()
    )
) = HistoryOrder(
    orderId = orderId,
    date = date,
    totalPrice = totalPrice,
    totalQuantity = totalQuantity,
    products = products
)

fun createHistoryProduct(
    productId: Long = Random.nextLong(),
    name: String = "name",
    description: String = "description",
    price: Float = 10.0f,
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