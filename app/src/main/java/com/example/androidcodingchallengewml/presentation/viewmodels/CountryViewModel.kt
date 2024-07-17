package com.example.androidcodingchallengewml.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcodingchallengewml.data.network.models.Country
import com.example.androidcodingchallengewml.data.repository.CountryRepository
import kotlinx.coroutines.flow.flow

//Manually injecting CountryRepository into CountryViewModel
class CountryViewModel(private val repo: CountryRepository = CountryRepository()): ViewModel() {

    private val _spinnerVisibility = MutableLiveData<Int>()
    val spinnerVisibility: LiveData<Int>
        get() = _spinnerVisibility

    fun getCountries() = flow{
        emit(CountryFetchingStatus.Loading)
        try{
            emit(CountryFetchingStatus.Success(repo.getCountries()))
        }
        catch(e: Exception){
            emit(CountryFetchingStatus.Error(e))
        }
    }

    fun setSpinnerVisibility(visibility: Int) {
        _spinnerVisibility.value = visibility
    }
}

//Representing different states of our network fetching of countries
sealed class CountryFetchingStatus{
    object Loading: CountryFetchingStatus()
    class Success(val countriesResponse: List<Country>): CountryFetchingStatus()
    class Error(val exception: Exception): CountryFetchingStatus()
}