package ca.six.unittestapp.todo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ca.six.unittestapp.todo.addedittask.AddEditTaskPresenterTest;
import ca.six.unittestapp.todo.data.source.TasksRepositoryTest;
import ca.six.unittestapp.todo.statistics.StatisticsPresenterTest;
import ca.six.unittestapp.todo.taskdetail.TaskDetailPresenterTest;
import ca.six.unittestapp.todo.tasks.TasksPresenterTest;

/**
 * @author hellenxu
 * @date 2017-09-23
 * Copyright 2017 Six. All rights reserved.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({AddEditTaskPresenterTest.class, TasksRepositoryTest.class,
        StatisticsPresenterTest.class, TaskDetailPresenterTest.class, TasksPresenterTest.class})
public class TodoUnitTestSuite {
}
