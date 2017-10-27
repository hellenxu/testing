package ca.six.unittestapp.todo.tasks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import ca.six.unittestapp.BuildConfig;
import ca.six.unittestapp.R;
import ca.six.unittestapp.todo.addedittask.AddEditTaskActivity;

import static org.junit.Assert.assertEquals;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-26.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TasksUnitTest {
    private TasksActivity activity;
    private FloatingActionButton fabAddTask;
    private ShadowApplication shadowApp;

    @Before public void setUp(){
        activity = Robolectric.setupActivity(TasksActivity.class);
        shadowApp = ShadowApplication.getInstance();
        fabAddTask = (FloatingActionButton) activity.findViewById(R.id.fab_add_task);
    }

    @Test public void openAddEditTaskPage() {
        // click add task button, and then jump to AddEditTaskActivity
        fabAddTask.performClick();

        Intent actualIt = shadowApp.getNextStartedActivity();
        Intent expectedIt = new Intent(activity, AddEditTaskActivity.class);

        // verify that AddEditTaskActivity is started.
        assertEquals(actualIt.getComponent(), expectedIt.getComponent());
    }

    private void createTask(String title, String description) {
        fabAddTask.performClick();

    }
}
