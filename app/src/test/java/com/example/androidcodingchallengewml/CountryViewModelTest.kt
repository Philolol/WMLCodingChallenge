package com.example.androidcodingchallengewml

import com.example.androidcodingchallengewml.data.network.models.Country
import com.example.androidcodingchallengewml.data.network.models.Currency
import com.example.androidcodingchallengewml.data.network.models.Language
import com.example.androidcodingchallengewml.data.network.requests.ApiService
import com.example.androidcodingchallengewml.data.repository.CountryRepository
import com.example.androidcodingchallengewml.presentation.viewmodels.CountryFetchingStatus
import com.example.androidcodingchallengewml.presentation.viewmodels.CountryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CountryViewModelTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var repo: CountryRepository
    private lateinit var viewModel: CountryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = CountryRepository(mockApiService)

        viewModel = CountryViewModel(repo)
    }

    @Test
    fun `getCountries emits Loading and then Success when repo returns countries`() = runTest {
        val countries = listOf(Country("Washington, D.C.", "US", Currency("USD", "United States Dollar", "$"), "https://restcountries.eu/data/usa.svg", Language("en", "English"), "United States of America", "NA"))
        `when`(mockApiService.getCountries()).thenReturn(countries)

        val result = viewModel.getCountries().toList()

        assertEquals(CountryFetchingStatus.Loading, result[0])
        assert(result[1] is CountryFetchingStatus.Success && (result[1] as CountryFetchingStatus.Success).countriesResponse == countries)
    }

    @Test
    fun `getCountries emits Loading and then Error when repo throws exception`() = runTest {
        val exception = RuntimeException("Network error")
        `when`(mockApiService.getCountries()).thenThrow(exception)

        val result = viewModel.getCountries().toList()

        assertEquals(CountryFetchingStatus.Loading, result[0])
        assert(result[1] is CountryFetchingStatus.Error && (result[1] as CountryFetchingStatus.Error).exception == exception)
    }
}