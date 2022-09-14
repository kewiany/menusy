package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Place
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val menuRepository: MenuRepository
) {

    suspend operator fun invoke(id: String): Result<PlaceData> = withContext(Dispatchers.IO) {
        try {
            val place = getPlace(id)
            val menus = getMenus(id)
            val data = PlaceData(
                place = place,
                menus = menus
            )
            delay(1500)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun getPlace(placeId: String): Place {
        return placeRepository.getPlace(placeId)
    }

    private suspend fun getMenus(placeId: String): List<Menu> {
        return menuRepository.getMenus(placeId)
    }
}

data class PlaceData(
    val place: Place,
    val menus: List<Menu>
)