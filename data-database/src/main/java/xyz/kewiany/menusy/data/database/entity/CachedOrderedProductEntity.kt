package xyz.kewiany.menusy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.kewiany.menusy.data.database.entity.CachedOrderedProductEntity.Companion.TABLE_NAME
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product

@Entity(tableName = TABLE_NAME)
data class CachedOrderedProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "product_id")
    val productId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "menu_id")
    val menuId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String? = null
) {
    companion object {
        const val TABLE_NAME = "ordered_product"
    }
}

fun OrderedProduct.toCachedOrderedProduct() = CachedOrderedProductEntity(
    quantity = quantity,
    productId = product.id,
    name = product.name,
    description = product.description,
    price = product.price.toFloat(),
    menuId = product.menuId,
    categoryId = product.categoryId
)

fun CachedOrderedProductEntity.toOrderedProduct() = OrderedProduct(
    quantity = quantity,
    product = Product(
        id = productId,
        name = name,
        description = description,
        price = price.toBigDecimal(),
        menuId = menuId,
        categoryId = categoryId
    )
)