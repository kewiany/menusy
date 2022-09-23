package xyz.kewiany.menusy.data

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.data.datastore.SearchTextHolder
import xyz.kewiany.menusy.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchTextHolder: SearchTextHolder
) : SearchRepository {

    override val searchText: Flow<String> = searchTextHolder.searchText

    override fun setSearchText(text: String) {
        searchTextHolder.setSearchText(text)
    }

    override fun clearSearchText() {
        searchTextHolder.setSearchText("")
    }
}