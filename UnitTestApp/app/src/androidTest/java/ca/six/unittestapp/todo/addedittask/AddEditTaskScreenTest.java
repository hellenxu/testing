package ca.six.unittestapp.todo.addedittask;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * @author hellenxu
 * @date 2017-09-25
 * Copyright 2017 Six. All rights reserved.
 */

@RunWith(AndroidJUnit4.class)
public class AddEditTaskScreenTest {
    @Rule
    public ActivityTestRule<AddEditTaskActivity> activityTestRule =
            new ActivityTestRule<AddEditTaskActivity>(AddEditTaskActivity.class, false, false);

    @Test
    public void addEmptyTaskErrorShow(){
        Intent addEditTaskIntent = new Intent(InstrumentationRegistry.getTargetContext(), AddEditTaskActivity.class);
        activityTestRule.launchActivity(addEditTaskIntent);

        onView(withId(R.id.add_task_title)).perform(clearText());
        onView(withId(R.id.add_task_description)).perform(clearText());
        onView(withId(R.id.fab_edit_task_done)).perform(click());

        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
        onView((withId(R.id.add_task_description))).check(matches(isDisplayed()));
    }
}
