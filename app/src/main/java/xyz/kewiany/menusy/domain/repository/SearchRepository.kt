package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    val searchText: Flow<String>
    fun setSearchText(text: String)
    fun clearSearchText()
}