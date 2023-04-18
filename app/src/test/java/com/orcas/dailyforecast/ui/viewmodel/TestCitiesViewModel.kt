package com.orcas.dailyforecast.ui.viewmodel

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.orcas.dailyforecast.repo.TestCitiesRepo
import com.orcas.data.model.city.City
import com.orcas.domain.usecase.city.GetCitiesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestCitiesViewModel {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CityViewModel
    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var testCitiesRepo: TestCitiesRepo

    @Before
    fun setUp() {

        testCitiesRepo = TestCitiesRepo()
        getCitiesUseCase = GetCitiesUseCase(testCitiesRepo)
        viewModel = CityViewModel(getCitiesUseCase)
    }


    @Test
    fun getCities_returnsCurrentList() {
        val currentList = listOf(
            City(1, "Cairo", 31.565656, 33.31467),
            City(2, "Alex", 29.565656, 31.31467),
            City(3, "Mansoura", 27.565656, 29.31467)
        )
        CoroutineScope(Dispatchers.Default).launch {
             testCitiesRepo.getCities(Application())?.collectLatest {
                 assertThat(it).isEqualTo(currentList)

             }
        }
    }
}
