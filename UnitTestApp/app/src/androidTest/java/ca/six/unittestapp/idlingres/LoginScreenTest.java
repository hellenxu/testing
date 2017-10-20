package ca.six.unittestapp.idlingres;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-19.
 */

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {
    @Rule public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);
    private IdlingResource idlingResource;

    private static final String TEST_NAME = "xxl";
    private static final String TEST_PWD = "56";
    private static final String TEST_RESULT_SUCCESS = "Welcome, xxl...";

    @Before public void setUp() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test public void loginWithValidInfoThenSuccess(){
        onView(withId(R.id.et_name)).perform(typeText(TEST_NAME), closeSoftKeyboard());
        onView(withId(R.id.et_pwd)).perform(typeText(TEST_PWD), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.tv_result)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_result)).check(matches(withText(TEST_RESULT_SUCCESS)));
        onView(withId(R.id.et_name)).check(matches(not(isDisplayed())));
    }

    @After public void clear(){
        if(idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
