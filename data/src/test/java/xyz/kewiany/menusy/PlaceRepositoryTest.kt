package xyz.kewiany.menusy

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.data.PlaceRepositoryImpl
import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.data.network.api.PlaceApi
import xyz.kewiany.menusy.data.network.response.PlaceResponse
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createPlace

class PlaceRepositoryTest : BaseTest() {

    private val placeId = "id1"
    private val place = createPlace(
        id = placeId
    )
    private val placeApi = mockk<PlaceApi> {
        coEvery { getPlace(placeId) } returns PlaceResponse(place)
    }
    private val placeDataStore = mockk<PlaceDataStore> {
        coJustRun { savePlace(any()) }
    }
    private val placeRepository = PlaceRepositoryImpl(placeApi, placeDataStore)

    @Test fun getPlace_andSavePlace() = testScope.runTest {
        val result = placeRepository.getPlace(placeId)

        coVerify { placeApi.getPlace(any()) }
        coVerify { placeDataStore.savePlace(place) }
        assertEquals(place, result)
    }
}