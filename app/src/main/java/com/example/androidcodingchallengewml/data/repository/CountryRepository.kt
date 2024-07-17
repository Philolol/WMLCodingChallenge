package com.example.androidcodingchallengewml.data.repository

import com.example.androidcodingchallengewml.data.network.requests.ApiService
import com.example.androidcodingchallengewml.data.network.requests.RetrofitClient

class CountryRepository(private val apiService: ApiService = RetrofitClient.api) {
    suspend fun getCountries() = apiService.getCountries()
}