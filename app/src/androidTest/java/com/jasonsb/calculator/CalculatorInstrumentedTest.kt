package com.jasonsb.calculator

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorInstrumentedTest {

    @Before
    fun startActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun fourPlusSixSum_ten() {
        listOf(R.id.four_button, R.id.add_button, R.id.six_button, R.id.sum_button).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("10")))
    }

    @Test
    fun thirteenMinusThreeSum_ten() {
        listOf(
            R.id.one_button,
            R.id.three_button,
            R.id.sub_button,
            R.id.three_button,
            R.id.sum_button
        ).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("10")))
    }

    @Test
    fun twentyTimesFiveSum_hundred() {
        listOf(
            R.id.two_button,
            R.id.zero_button,
            R.id.mul_button,
            R.id.five_button,
            R.id.sum_button
        ).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("100")))
    }

    @Test
    fun seventyDivSevenSum_ten() {
        listOf(
            R.id.seven_button,
            R.id.zero_button,
            R.id.div_button,
            R.id.seven_button,
            R.id.sum_button
        ).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("10")))
    }

    @Test
    fun twoPointFiveTimesFourSum_ten() {
        listOf(
            R.id.two_button,
            R.id.dot_button,
            R.id.five_button,
            R.id.mul_button,
            R.id.four_button,
            R.id.sum_button
        ).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("10")))
    }

    @Test
    fun fourDivFiveSumTimesTwelvePointFiveSum() {
        listOf(
            R.id.four_button,
            R.id.div_button,
            R.id.five_button,
            R.id.sum_button,
            R.id.mul_button,
            R.id.one_button,
            R.id.two_button,
            R.id.dot_button,
            R.id.five_button,
            R.id.sum_button
        ).forEach {
            onView(withId(it)).perform(click())
        }
        onView(withId(R.id.result_text)).check(matches(withText("10")))
    }
}