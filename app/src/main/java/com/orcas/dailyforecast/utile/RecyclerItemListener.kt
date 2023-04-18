package com.orcas.dailyforecast.utile

import com.orcas.data.model.city.City

interface RecyclerItemListener {
    fun onItemSelected(city: City )

}