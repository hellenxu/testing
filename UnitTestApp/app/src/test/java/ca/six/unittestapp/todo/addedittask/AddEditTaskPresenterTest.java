package ca.six.unittestapp.todo.addedittask;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.UUID;

import ca.six.unittestapp.todo.data.Task;
import ca.six.unittestapp.todo.data.source.TasksDataSource;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author hellenxu
 * @date 2017-09-24
 * Copyright 2017 Six. All rights reserved.
 */

public class AddEditTaskPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TasksDataSource mockTasksRepository;
    @Mock
    private AddEditTaskContract.View mockAddTaskView;
    private AddEditTaskPresenter presenter;
    private String taskId;

    @Before
    public void setUp() {
        taskId = UUID.randomUUID().toString();
        presenter = new AddEditTaskPresenter(taskId, mockTasksRepository, mockAddTaskView);
        when(mockAddTaskView.isActive()).thenReturn(true);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void cfTest() {
        verify(mockAddTaskView).setPresenter(presenter);
    }

    @Test
    public void startSuccess() {
        presenter.start();
        verify(mockTasksRepository).getTask(taskId, presenter);
    }

    @Test
    public void saveTaskThenCreateNewTaskSuccess() {
        presenter = new AddEditTaskPresenter(null, mockTasksRepository, mockAddTaskView);
        presenter.saveTask("Title1", "Description1");
        verify(mockTasksRepository).saveTask(any(Task.class));
        verify(mockAddTaskView).showTasksList();
    }

    @Test
    public void saveTaskThenCreateEmptyTask() {
        presenter = new AddEditTaskPresenter(null, mockTasksRepository, mockAddTaskView);
        presenter.saveTask("", "");
        verify(mockAddTaskView).showEmptyTaskError();
    }

    @Test
    public void saveTaskThenUpdateTask() {
        presenter.saveTask("Title1", "Description1");
        verify(mockTasksRepository).saveTask(any(Task.class));
        verify(mockAddTaskView).showTasksList();
    }

    @Test
    public void populateTaskRepoGetTaskViewUpdate(){
        ArgumentCaptor<TasksDataSource.GetTaskCallback> getTaskCaptor =
                ArgumentCaptor.forClass(TasksDataSource.GetTaskCallback.class);
        Task testTask = new Task("Title1", "Description1");
        presenter = new AddEditTaskPresenter(testTask.getId(), mockTasksRepository, mockAddTaskView);

        presenter.populateTask();
        verify(mockTasksRepository).getTask(eq(testTask.getId()), getTaskCaptor.capture());

        getTaskCaptor.getValue().onTaskLoaded(testTask);
        verify(mockAddTaskView).setTitle(testTask.getTitle());
        verify(mockAddTaskView).setDescription(testTask.getDescription());

        getTaskCaptor.getValue().onDataNotAvailable();
        verify(mockAddTaskView).showEmptyTaskError();
    }

    @Test(expected = RuntimeException.class)
    public void populateTaskWithNewTaskThrowException() {
        presenter = new AddEditTaskPresenter(null, mockTasksRepository, mockAddTaskView);
        presenter.populateTask();
    }
}
