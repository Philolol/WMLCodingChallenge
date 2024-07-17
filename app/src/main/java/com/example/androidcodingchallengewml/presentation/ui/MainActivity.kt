package com.example.androidcodingchallengewml.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidcodingchallengewml.R
import com.example.androidcodingchallengewml.databinding.ActivityMainBinding
import com.example.androidcodingchallengewml.presentation.ui.fragments.CountryFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.container.id, CountryFragment.newInstance(), CountryFragment.TAG).commit()
    }
}