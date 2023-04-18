package com.orcas.dailyforecast.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.orcas.dailyforecast.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
            allOf(
                withId(R.id.tv_activity_main_toolbar_search), withText("Select Country"),
                withParent(
                    allOf(
                        withId(R.id.cl_activity_main_toolbar_search),
                        withParent(withId(R.id.cl_activity_main_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Select Country")))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_activity_main_try_again), withText("Try Again"),
                withParent(
                    allOf(
                        withId(R.id.cl_activity_main_container),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Try Again")))

        val imageView = onView(
            allOf(
                withId(R.id.iv_activity_main_no_data),
                withParent(
                    allOf(
                        withId(R.id.cl_activity_main_container),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.iv_activity_main_toolbar_search),
                withParent(
                    allOf(
                        withId(R.id.cl_activity_main_toolbar_search),
                        withParent(withId(R.id.cl_activity_main_container))
                    )
                ),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
    }
}
