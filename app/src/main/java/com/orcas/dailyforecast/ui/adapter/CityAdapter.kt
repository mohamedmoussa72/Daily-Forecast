package com.orcas.dailyforecast.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.orcas.dailyforecast.databinding.ItemCityTextBinding
import com.orcas.dailyforecast.ui.viewmodel.WeatherViewModel
import com.orcas.dailyforecast.utile.RecyclerItemListener
import com.orcas.data.model.city.City
import javax.inject.Inject


class CityAdapter @Inject constructor () :
    RecyclerView.Adapter<CityAdapter.RecipeViewHolder>() {
   private var cities :MutableList<City> = mutableListOf()
    private var onItemClickListener: RecyclerItemListener ?= null

    fun setClickListener( onItemClickListener: RecyclerItemListener){
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = ItemCityTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        onItemClickListener?.let { holder.bind(cities[position], it) }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setList(cities:List<City>){
        this.cities.clear()
        this.cities = cities.toMutableList()
        notifyDataSetChanged()
    }

    class RecipeViewHolder(private val itemBinding: ItemCityTextBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(element:City, recyclerItemListener: RecyclerItemListener) {
            itemBinding.tvItemCityName.text = element.cityNameEn
            itemBinding.clItemCityContainer.setOnClickListener { recyclerItemListener.onItemSelected(element) }
        }
    }
}

