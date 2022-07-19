package xyz.kewiany.menusy.viewmodel

import createProduct
import createText
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.domain.usecase.menu.GetProductsByQueryResult
import xyz.kewiany.menusy.domain.usecase.menu.GetProductsByQueryUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.GetSearchTextUseCase
import xyz.kewiany.menusy.presentation.features.search.SearchViewModel
import xyz.kewiany.menusy.presentation.utils.ProductMapper
import xyz.kewiany.menusy.presentation.utils.ProductUiItem
import kotlin.random.Random

class SearchViewModelTest : BaseTest() {

    private val sampleText1 = createText()
    private val sampleText2 = createText()

    private val _searchText = MutableStateFlow("")
    private val clearSearchTextUseCase = mockk<ClearSearchTextUseCase>()
    private val getOrderedProductsUseCase = mockk<GetOrderedProductsUseCase> {
        coEvery { this@mockk.invoke() } returns emptyList()
    }
    private val getProductsByQueryUseCase = mockk<GetProductsByQueryUseCase> {
        coEvery { this@mockk.invoke(any()) } returns GetProductsByQueryResult.Success(emptyList())
    }
    private val getSearchTextUseCase = mockk<GetSearchTextUseCase> {
        coEvery { this@mockk.invoke() } returns _searchText
    }
    private val updateOrderUseCase = mockk<UpdateOrderUseCase>()

    private fun viewModel() = SearchViewModel(
        clearSearchTextUseCase,
        getOrderedProductsUseCase,
        getProductsByQueryUseCase,
        getSearchTextUseCase,
        updateOrderUseCase
    )

    @Test
    fun given_noSearchText_then_noResults() = testScope.runTest {
        val state = viewModel().state
        val expected = emptyList<String>()

        _searchText.value = ""
        advanceTimeBy(600)

        val actual = state.value.results
        coVerify(exactly = 0) { getProductsByQueryUseCase(any()) }
        assertEquals(expected, actual)
    }

    @Test
    fun when_searchText_then_setResults() = testScope.runTest {
        val state = viewModel().state
        val products = listOf(
            createProduct(id = "id1"), createProduct(id = "id2")
        )
        coEvery { getProductsByQueryUseCase(sampleText1) } returns GetProductsByQueryResult.Success(products)
        val expected = products.map { product -> ProductMapper.map(product) }

        _searchText.value = sampleText1
        advanceTimeBy(600)

        val actual = state.value.results
        assertEquals(expected, actual)
    }

    @Test
    fun given_searchText_and_results_when_noSearchText_then_noResults() = testScope.runTest {
        val state = viewModel().state
        val products = listOf(
            createProduct(id = "id1"), createProduct(id = "id2")
        )
        coEvery { getProductsByQueryUseCase(sampleText1) } returns GetProductsByQueryResult.Success(products)
        val expected = emptyList<ProductUiItem>()

        _searchText.value = sampleText1
        advanceTimeBy(600)
        _searchText.value = ""
        advanceTimeBy(600)

        val actual = state.value.results
        assertEquals(expected, actual)
        coVerify(exactly = 1) { getProductsByQueryUseCase(any()) }
    }

    @Test
    fun when_searchTextTwice_then_setResultsOnce() = testScope.runTest {
        viewModel()

        _searchText.value = sampleText1
        advanceTimeBy(600)
        _searchText.value = sampleText2
        advanceTimeBy(400)

        coVerify(exactly = 1) { getProductsByQueryUseCase(any()) }
    }

    @Test
    fun when_searchTextTwice_then_setResultsTwice() = testScope.runTest {
        viewModel()

        _searchText.value = sampleText1
        advanceTimeBy(600)
        val secondText = Random.nextLong().toString()
        _searchText.tryEmit(secondText)
        advanceTimeBy(600)

        coVerify(exactly = 2) { getProductsByQueryUseCase(any()) }
    }
}