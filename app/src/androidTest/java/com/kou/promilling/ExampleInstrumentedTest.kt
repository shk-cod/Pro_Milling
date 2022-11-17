package com.kou.promilling

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun cuttingWidthFlowTest() {
        onView(withId(R.id.cutting_width))
            .perform(click())

        onView(withId(R.id.text_input_radius))
            .perform(typeText("5"))
        onView(withId(R.id.text_input_rounding_radius))
            .perform(typeText("10"))
        onView(withId(R.id.text_input_cutting_width))
            .perform(typeText("15"))

        onView(withId(R.id.button_result))
            .perform(click())

        onView(withId(R.id.text_view_result))
            .check(matches(withText("")))
    }
}