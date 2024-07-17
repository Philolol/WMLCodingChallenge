package com.example.androidcodingchallengewml.data.network.models

data class Language(
    val code: String,
    val name: String,
    val iso639_2: String? = null,
    val nativeName: String? = null,
)
