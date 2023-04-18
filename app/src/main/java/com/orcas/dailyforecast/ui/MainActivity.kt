package com.orcas.dailyforecast.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orcas.dailyforecast.R
import com.orcas.dailyforecast.base.BaseActivity
import com.orcas.dailyforecast.databinding.ActivityMainBinding

import com.orcas.dailyforecast.ui.adapter.CityAdapter
import com.orcas.dailyforecast.ui.viewmodel.CityViewModel
import com.orcas.dailyforecast.ui.viewmodel.WeatherViewModel
import com.orcas.dailyforecast.utile.*
import com.orcas.dailyforecast.utile.ConstantApp.CLEAR
import com.orcas.dailyforecast.utile.ConstantApp.CLOUdS
import com.orcas.dailyforecast.utile.ConstantApp.RAIN
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.city.City
import com.orcas.data.utile.DataResult
import com.orcas.data.utile.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), RecyclerItemListener {
    @Inject
    lateinit var adapter: CityAdapter

    private lateinit var binding: ActivityMainBinding
    private val cityViewModel: CityViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            rvActivityMainCitiesList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvActivityMainCitiesList.adapter = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityViewModel.getCities(this)
        adapter.setClickListener(this)
        eventGUI()

    }

    private fun eventGUI() {
        binding.apply {
            ivActivityMainToolbarSearch.setOnClickListener {
                showListLoadingView(true)
            }
            tvActivityMainToolbarSearch.setOnClickListener {
                showListLoadingView(true)

            }
            clActivityMainContainer.setOnClickListener {
                clActivityMainCitiesList.toGone()
            }
            tvActivityMainTryAgain.setOnClickListener {
                binding.tvActivityMainTryAgain.toGone()
                cityViewModel.getCities(this@MainActivity)
                showListLoadingView(true)
            }
        }
    }


    private fun handleCityList(data: DataResult<List<City>>) {
        when (data.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> data.data?.let {
                if (data.data!!.isNotEmpty())
                    bindListData(cities = it)
                else
                    showNoData()
            }
            Status.ERROR -> {
                if (data.data!!.isEmpty())
                    showNoData()
            }
            Status.No_NETWORK -> {
            }
        }
    }

    private fun showNoData() {
        binding.tvActivityMainTryAgain.toVisible()
        cityViewModel.showToastMessage(getString(com.orcas.data.R.string.message_no_network_connected_str))
    }

    private fun bindListData(cities: List<City>) {
        if (cities.isNotEmpty()) {
            adapter.setList(cities)
        }
    }

    private fun showListLoadingView(isShow: Boolean) {
        binding.clActivityMainCitiesList.visibility = if (isShow) View.VISIBLE else View.GONE

    }

    override fun observeViewModel() {
        observe(cityViewModel.citiesLiveData, ::handleCityList)
        observe(weatherViewModel.weatherLiveData, ::handleWeatherData)
        observeToast(cityViewModel.showToast)
    }


    override fun onItemSelected(city: City) {
        binding.tvActivityMainToolbarSearch.text = city.cityNameEn
        weatherViewModel.getWeather(this,city.id, city.lat, city.lon)
        showListLoadingView(false)
    }

    private fun handleWeatherData(data: DataResult<WeatherDataDB>) {
        when (data.status) {
            Status.LOADING -> showDataLoadingView(true)
            Status.SUCCESS -> data.data?.let {

                bindWeatherData(data = it)
            }
            Status.ERROR -> {
                showNoDataWeather(data)
            }
            Status.No_NETWORK -> {
                if (data.data.toString() != "null")
                    cityViewModel.showToastMessage( getString(R.string.message_approximate_results))

            }
        }
    }

    private fun showNoDataWeather(data: DataResult<WeatherDataDB>) {
        showListLoadingView(false)
        binding.tvActivityMainTryAgain.toVisible()
        cityViewModel.showToastMessage(data.message.toString())
        binding.ivActivityMainNoData.toVisible()
        binding.pbDataLoading.toGone()
    }

    private fun bindWeatherData(data: WeatherDataDB) {
        binding.apply {
            when (data.weatherData.weatherList[0].main) {
                RAIN -> {
                    clActivityMainWeatherContainer.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_rain)
                }
                CLOUdS -> {
                    clActivityMainWeatherContainer.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_cloud)
                }
                else -> {
                    clActivityMainWeatherContainer.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_clear)
                }
            }
            tvActivityMainCityName.text = data.citName
            tvActivityMainWindSpeed.text = data.weatherData.wind.speed.toString()
            tvActivityMainStatus.text = data.weatherData.weatherList[0].main
            tvActivityMainDegree.text = data.weatherData.main.temp.toString()
            tvActivityMainMinDegree.text = data.weatherData.main.temp_min.toString()
            tvActivityMainMaxDegree.text = data.weatherData.main.temp_max.toString()
            binding.clActivityMainWeatherContainer.toVisible()
            binding.pbDataLoading.toGone()

        }
    }

    private fun showDataLoadingView(isShow: Boolean) {
        binding.pbDataLoading.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.tvActivityMainTryAgain.visibility = if (!isShow) View.VISIBLE else View.GONE
        binding.clActivityMainWeatherContainer.visibility = if (!isShow) View.VISIBLE else View.GONE
        binding.ivActivityMainNoData.visibility = if (!isShow) View.VISIBLE else View.GONE

    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)

    }
}