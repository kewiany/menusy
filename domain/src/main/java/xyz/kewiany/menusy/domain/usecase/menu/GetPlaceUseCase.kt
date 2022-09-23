package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(GetPlaceUseCase::class.java)

    suspend operator fun invoke(id: String): Result<PlaceData> = withContext(Dispatchers.IO) {
        try {
            val place = getPlace(id)
            val menus = getMenus(id)
            val data = PlaceData(
                place = place,
                menus = menus
            )
            Result.Success(data)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            logger.warn(e.stackTraceToString(), e)
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