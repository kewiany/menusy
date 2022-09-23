package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.data.source.remote.response.PlaceResponse

interface PlaceApi {
    suspend fun getPlace(id: String): PlaceResponse?
}