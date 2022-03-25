package com.saadfauzi.githubuser.ui.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.saadfauzi.githubuser.R
import com.saadfauzi.githubuser.adapters.RecyclerUserAdapter
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : TestCase() {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun navViewTest() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.scrollToPosition<RecyclerUserAdapter.ViewHolder>(6))
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.img_empty_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_empty_fav)).check(matches(isDisplayed()))
    }

    @Test
    fun rvTest() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerUserAdapter.ViewHolder>(1,
            click()))
        onView(withId(R.id.detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_circle_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_username)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_company)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_location)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_value_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_value_following)).check(matches(isDisplayed()))
    }
}