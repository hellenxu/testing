package ca.six.unittestapp.contrib;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-20.
 */

@RunWith(AndroidJUnit4.class)
public class ItemListScreenTest {
    @Rule public ActivityTestRule<ItemListActivity> activityTestRule =
            new ActivityTestRule<ItemListActivity>(ItemListActivity.class);

    private static final int LAST_ITEM_POS = 40;

    @Test public void scrollToBottom(){
        onView(withId(R.id.rv_items)).perform(actionOnItemAtPosition(LAST_ITEM_POS, click()));

        String expected = activityTestRule.getActivity().getResources().getString(R.string.item) + LAST_ITEM_POS;
        onView(withText(expected)).check(matches(isDisplayed()));
    }
}
