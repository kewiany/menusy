package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.domain.model.Place

interface PlaceRepository {
    suspend fun getPlace(id: String): Place
}