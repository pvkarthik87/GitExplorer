package com.karcompany;

/**
 * Created by sammyer on 2016-10-18.
 */

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.views.activities.BrowseUsersActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BrowseUsersActivityTest {

	@Rule
	public ActivityTestRule<BrowseUsersActivity> activityRule = new ActivityTestRule<>(BrowseUsersActivity.class);

	@Test
	public void testUserProfileNavigation() {
		SystemClock.sleep(3000);
		onView(allOf(withId(R.id.user_list), isDisplayed())).perform(
				RecyclerViewActions.actionOnItemAtPosition(2, click()));
		onView(withText("Followers")).check(matches(isDisplayed()));
		onView(allOf(withId(R.id.repoList), isDisplayed())).perform(
				RecyclerViewActions.actionOnItemAtPosition(2, click()));
	}

}
