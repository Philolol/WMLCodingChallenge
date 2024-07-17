package com.example.androidcodingchallengewml.presentation.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallengewml.R
import com.example.androidcodingchallengewml.data.network.models.Country
import com.example.androidcodingchallengewml.databinding.CountryItemBinding

class CountryAdapter: ListAdapter<Country, CountryItemViewHolder>(ItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val country = getItem(position)
        holder.bind(country, holder.itemView)
    }

    fun updateCountries(newCountries: List<Country>){
        submitList(newCountries)
    }
}


class CountryItemViewHolder(private val binding: CountryItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(country: Country, view: View){
        with(binding){
            nameAndRegion.text = view.context.getString(R.string.formatted_name_and_region, country.name, country.region)
            code.text = country.code
            capital.text = country.capital
        }
    }
}

class ItemDiffUtil: DiffUtil.ItemCallback<Country>(){
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }

}