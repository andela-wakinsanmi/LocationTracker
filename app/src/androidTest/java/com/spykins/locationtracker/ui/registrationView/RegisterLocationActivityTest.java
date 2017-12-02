package com.spykins.locationtracker.ui.registrationView;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import android.content.res.Resources;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spykins.locationtracker.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterLocationActivityTest {
    @Rule
    public ActivityTestRule<RegisterLocationActivity> mActivityTestRule =
            new ActivityTestRule<>(RegisterLocationActivity.class);
    Resources res;

    @Before
    public void setUp() {
        res = getInstrumentation().getTargetContext().getResources();

    }


    @Test
    public void registerButtonClickedWithoutInputingAdress() throws Exception {
        onView(withId(R.id.submitValue)).perform(click());
        onView(withId(R.id.textViewLocationError)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewLocationError)).check(matches(withText(res.getString(R.string.address_is_empty))));
    }

    @Test
    public void registerButtonClickedWithoutInputingLocation() throws Exception {
        onView(withId(R.id.addressEditText)).perform(typeText("55 Moleye Street"), closeSoftKeyboard());
        onView(withId(R.id.submitValue)).perform(click());
        onView(withId(R.id.textViewLocationError)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewLocationError)).check(matches(withText(res.getString(R.string.location_is_empty))));
    }

    @Test
    public void registerButtonClickedWithAllInput() throws Exception {
        onView(withId(R.id.addressEditText)).perform(typeText("55 Moleye Street"), closeSoftKeyboard());
        onView(withId(R.id.longitudeEditText)).perform(typeText("23"), closeSoftKeyboard());
        onView(withId(R.id.latitudeEditText)).perform(typeText("23"), closeSoftKeyboard());
        onView(withId(R.id.submitValue)).perform(click());
    }



}