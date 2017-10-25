package ca.six.unittestapp.intents;

import android.app.Instrumentation;
import android.net.Uri;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ca.six.unittestapp.Constants.DATA_HANDLE_RESULT;
import static ca.six.unittestapp.Constants.INTENTS_ACTION;
import static ca.six.unittestapp.Constants.INTENTS_CATEGORY;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-25.
 */

@RunWith(AndroidJUnit4.class)
public class DefaultActivityTest {
    @Rule public IntentsTestRule<DefaultActivity> intentsTestRule
            = new IntentsTestRule<DefaultActivity>(DefaultActivity.class, false);

    private static final String TEST_INPUT = "";

    @Test public void initialIntentSuccess( ){
        //input data to send
        onView(withId(R.id.et_input)).perform(typeText(TEST_INPUT), closeSoftKeyboard());
        onView(withId(R.id.btn_send)).perform(click());

        //verify outgoing intents
        intended(allOf(hasAction(INTENTS_ACTION),
                hasCategories(hasItem(equalTo(INTENTS_CATEGORY))),
                hasData(Uri.parse("input:" + TEST_INPUT)),
                toPackage("ca.six.unittestapp.mock")));

        //receive incoming intents
        intending(allOf(hasExtra(DATA_HANDLE_RESULT,"success"),
                hasComponent(hasShortClassName(".DataHandleActivity"))));
    }
}
