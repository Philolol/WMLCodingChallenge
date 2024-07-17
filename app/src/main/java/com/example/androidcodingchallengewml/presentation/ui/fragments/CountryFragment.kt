package com.example.androidcodingchallengewml.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.androidcodingchallengewml.R
import com.example.androidcodingchallengewml.databinding.CountriesFragmentBinding
import com.example.androidcodingchallengewml.presentation.factory.CountryViewModelFactory
import com.example.androidcodingchallengewml.presentation.ui.adapter.CountryAdapter
import com.example.androidcodingchallengewml.presentation.viewmodels.CountryFetchingStatus
import com.example.androidcodingchallengewml.presentation.viewmodels.CountryViewModel
import kotlinx.coroutines.launch

class CountryFragment: Fragment() {

    companion object{
        const val TAG = "COUNTRY_FRAGMENT"

        fun newInstance() = CountryFragment()
    }

    private lateinit var binding: CountriesFragmentBinding
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountriesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countryAdapter = CountryAdapter()
        binding.recyclerView.adapter = countryAdapter
        setupViewModel()
        fetchCountries()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setupViewModel(){
        val vmFactory = CountryViewModelFactory()
        countryViewModel = ViewModelProvider(this, vmFactory)[CountryViewModel::class.java]
    }

    private fun fetchCountries() {

        countryViewModel.spinnerVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.spinner.visibility = visibility
        }

        lifecycleScope.launch {
            countryViewModel.getCountries().collect { command ->
                when(command){
                    CountryFetchingStatus.Loading -> {
                        countryViewModel.setSpinnerVisibility(View.VISIBLE)
                        binding.spinner.visibility = View.VISIBLE
                    }
                    is CountryFetchingStatus.Error -> {
                        countryViewModel.setSpinnerVisibility(View.GONE)
                        activity?.let{ act ->
                            Toast.makeText(act.applicationContext, getString(R.string.country_fetching_error), Toast.LENGTH_SHORT)
                        }
                    }
                    is CountryFetchingStatus.Success -> {
                        countryViewModel.setSpinnerVisibility(View.GONE)
                        countryAdapter.updateCountries(command.countriesResponse)
                    }
                }
            }
        }

    }


}