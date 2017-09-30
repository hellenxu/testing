package ca.six.unittestapp.todo.tasks;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.six.unittestapp.R;
import ca.six.unittestapp.todo.Injection;
import ca.six.unittestapp.todo.Utils;
import ca.six.unittestapp.todo.data.Task;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-09-27.
 */

@RunWith(AndroidJUnit4.class)
public class TaskScreenTest {

    @Rule public ActivityTestRule<TasksActivity> activityTestRule =
            new ActivityTestRule<TasksActivity>(TasksActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    Injection.provideTasksRepository(InstrumentationRegistry.getTargetContext())
                            .deleteAllTasks();
                }
            };

    private static final String TITLE = "TITLE";
    private static final String TITLE1 = "TITLE1";
    private static final String DESCRIPTION = "DESCRIPTION";

    private static Matcher<Object> withItemTitle(final String title) {
        return new BoundedMatcher<Object, Task>(Task.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with title: " + title);
            }

            @Override
            protected boolean matchesSafely(Task item) {
                final String taskTitle = item.getTitle();
                return taskTitle != null && taskTitle.equals(title);
            }
        };
    }

    @Test public void openAddEditTaskScreen(){
        onView(withId(R.id.fab_add_task)).perform(click());

        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
        onView(withId(R.id.add_task_description)).check(matches(isDisplayed()));
    }

    private void createTask(String title, String description) {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.add_task_title)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.add_task_description)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.fab_edit_task_done)).perform(click());
    }

    @Test public void addNewTaskSuccess() {
        createTask(TITLE, DESCRIPTION);

        onView(withId(R.id.fab_add_task)).check(matches(isDisplayed()));
        onView(withText(TITLE)).check(matches(isDisplayed()));
        onData(withItemTitle(TITLE)).check(matches(isDisplayed()));
    }

    private void viewAllTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_all)).perform(click());
    }

    private void viewCompletedTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_completed)).perform(click());
    }

    private void viewActiveTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_active)).perform(click());
    }

    private void clickCheckBox(String title) {
        onView(allOf(withId(R.id.complete), hasSibling(withText(title)))).perform(click());
    }

    @Test public void markTaskAsCompleted(){
        createTask(TITLE, DESCRIPTION);
        clickCheckBox(TITLE);
        viewCompletedTasks();

        onView(withText(TITLE)).check(matches(isDisplayed()));
    }

    @Test public void rotateScreenNothingChanges(){
        createTask(TITLE, DESCRIPTION);
        clickCheckBox(TITLE);
        viewCompletedTasks();

        onView(withText(TITLE)).check(matches(isDisplayed()));

        Utils.changeOrientation(activityTestRule.getActivity());

        onView(withText(TITLE)).check(matches(isDisplayed()));
        onView(withText(R.string.label_completed)).check(matches(isDisplayed()));
    }

    @Test public void createMultipleTasks(){
        createTask(TITLE, DESCRIPTION);
        createTask(TITLE1, DESCRIPTION);

        onData(withItemTitle(TITLE)).check(matches(isDisplayed()));
        onData(withItemTitle(TITLE1)).check(matches(isDisplayed()));
    }
}
