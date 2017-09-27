package ca.six.unittestapp.todo.addedittask;

import android.widget.EditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ca.six.unittestapp.BuildConfig;
import ca.six.unittestapp.R;

/**
 * @author hellenxu
 * @date 2017-09-26
 * Copyright 2017 Six. All rights reserved.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AddEditTaskScreenTest {
    private AddEditTaskActivity activity;
    private EditText etTaskTitle, etTaskDescription;

    @Before public void setUp(){
        activity = Robolectric.setupActivity(AddEditTaskActivity.class);
        etTaskTitle = (EditText) activity.findViewById(R.id.add_task_title);
        etTaskDescription = (EditText) activity.findViewById(R.id.add_task_description);
    }

    @Test public void addEmptyTaskErrorShow(){
        etTaskTitle.setText("");
        etTaskDescription.setText("");
        activity.findViewById(R.id.fab_edit_task_done).performClick();

        Assert.assertTrue(activity.findViewById(R.id.add_task_title).isShown());
    }

}
