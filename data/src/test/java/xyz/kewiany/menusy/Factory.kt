package xyz.kewiany.menusy

import xyz.kewiany.menusy.data.database.entity.CachedOrderedProductEntity
import xyz.kewiany.menusy.data.database.entity.OrderEntity
import xyz.kewiany.menusy.data.database.entity.OrderWithProductsEntity
import xyz.kewiany.menusy.data.database.entity.ProductEntity

fun createCachedOrderedProductEntity(
    id: Long = 0,
    quantity: Int = 0,
    productId: String = "productId",
    name: String = "name",
    description: String = "description",
    price: Float = 12.25f,
    menuId: String = "menuId",
    categoryId: String? = null
) = CachedOrderedProductEntity(
    id = id,
    quantity = quantity,
    productId = productId,
    name = name,
    description = description,
    price = price,
    menuId = menuId,
    categoryId = categoryId
)

fun createOrderEntity(
    id: Long = 1010L,
    date: String = "2019-03-20T15:58:00",
    totalQuantity: Int = 2,
    totalPrice: Float = 10.10f,
    placeName: String = "placeName",
    placeAddress: String = "placeAddress"
) = OrderEntity(
    id = id,
    date = date,
    totalQuantity = totalQuantity,
    totalPrice = totalPrice,
    placeName = placeName,
    placeAddress = placeAddress
)

fun createProductEntity(
    id: Long = 1010L,
    orderId: Long = 1010L,
    name: String = "name",
    description: String = "description",
    price: Float = 10.10f,
    quantity: Int = 1
) = ProductEntity(
    id = id,
    orderId = orderId,
    name = name,
    description = description,
    price = price,
    quantity = quantity
)

fun createOrderWithProductsEntity(
    orderEntity: OrderEntity = createOrderEntity(),
    products: List<ProductEntity> = listOf(
        createProductEntity(),
        createProductEntity(),
    )
) = OrderWithProductsEntity(
    order = orderEntity,
    products = products
)