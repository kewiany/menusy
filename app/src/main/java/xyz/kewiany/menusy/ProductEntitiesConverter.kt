package xyz.kewiany.menusy

import androidx.room.TypeConverter
import com.google.gson.Gson
import xyz.kewiany.menusy.db.ProductEntity

class ProductEntitiesConverter {
    @TypeConverter
    fun listToJson(value: List<ProductEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ProductEntity>::class.java).toList()
}