package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.model.Place
import xyz.kewiany.menusy.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(private val placeRepository: PlaceRepository) {

    suspend operator fun invoke(id: String): Result<Place> = withContext(Dispatchers.IO) {
        try {
            val data = placeRepository.getPlace(id)
            delay(1500)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}