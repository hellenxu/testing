package ca.six.unittestapp.multiprocesses;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-24.
 */

@RunWith(AndroidJUnit4.class)
public class PrimaryActivityTest {
    @Rule public ActivityTestRule<PrimaryActivity> activityTestRule =
            new ActivityTestRule<PrimaryActivity>(PrimaryActivity.class);
    private static final String PRIMARY_PROCESS_NAME = "ca.six.unittestapp.mock";
    private static final String SECOND_PROCESS_NAME = "ca.six.unittestapp.mock:second";

    /**
     * Multiprocess Espresso must target Android 8.0(API level 26) or higher
     * and that's the reason why below test fails.
     * Need to use Android 3.x and change targetSdk as well.
     * */
    @Test public void jumpToOtherProcessSuccess() {
        //check primary process name at first
        onView(withId(R.id.tv_process_name)).check(matches(withText(PRIMARY_PROCESS_NAME)));

        //jump to another process
        onView(withId(R.id.btn_jump)).perform(click());

        //check current process name
        onView(withId(R.id.tv_process_name)).check(matches(withText(SECOND_PROCESS_NAME)));
    }
}
