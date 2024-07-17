package com.example.androidcodingchallengewml.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidcodingchallengewml.data.repository.CountryRepository
import com.example.androidcodingchallengewml.presentation.viewmodels.CountryViewModel

class CountryViewModelFactory(val repo: CountryRepository = CountryRepository()):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CountryViewModel::class.java))
            return CountryViewModel(repo) as T

        return throw IllegalArgumentException("Error creating CountryViewModelFactory")
    }
}