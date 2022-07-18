package xyz.kewiany.menusy.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchTextUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(): Flow<String> {
        return searchRepository.searchText
    }
}