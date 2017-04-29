package ca.six.unittestapp.todo.tasks;

import android.app.Activity;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import ca.six.unittestapp.todo.addedittask.AddEditTaskActivity;
import ca.six.unittestapp.todo.data.Task;
import ca.six.unittestapp.todo.data.source.TasksDataSource;
import ca.six.unittestapp.todo.data.source.TasksRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-04-27.
 */

public class TasksPresenterTest {
    private TasksRepository mMockTasksRepository;
    private TasksContract.View mMockTasksView;
    private TasksPresenter mTasksPresenter;
    private Task mTestTask;
    private ArgumentCaptor<TasksDataSource.LoadTasksCallback> mLoadTaskCallbackCaptor
            = ArgumentCaptor.forClass(TasksDataSource.LoadTasksCallback.class);
    private ArgumentCaptor<List> mTaskListCaptor = ArgumentCaptor.forClass(List.class);

    // include one uncompleted task and two completed tasks
    private final List<Task> TASKS
            = Lists.newArrayList(new Task("Title1", "Description1"),
            new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));

    @Before
    public void setUp(){
        mMockTasksRepository = mock(TasksRepository.class);
        mMockTasksView = mock(TasksContract.View.class);
        mTasksPresenter = new TasksPresenter(mMockTasksRepository, mMockTasksView);
        mTestTask = new Task("test", "test for sure");
        when(mMockTasksView.isActive()).thenReturn(true);
    }

    @Test
    public void cfTest(){
        verify(mMockTasksView).setPresenter(mTasksPresenter);
    }

    @Test
    public void resultTest(){
        mTasksPresenter.result(AddEditTaskActivity.REQUEST_ADD_TASK, Activity.RESULT_OK);
        verify(mMockTasksView).showSuccessfullySavedMessage();
    }

    @Test
    public void loadTasksOnDataNotAvailableTest(){
        ArgumentCaptor<TasksDataSource.LoadTasksCallback> loadTasksCbCaptor
                = ArgumentCaptor.forClass(TasksDataSource.LoadTasksCallback.class);

        mTasksPresenter.loadTasks(true);

        verify(mMockTasksView).setLoadingIndicator(true);
        verify(mMockTasksRepository).refreshTasks();
        verify(mMockTasksRepository).getTasks(loadTasksCbCaptor.capture());
        loadTasksCbCaptor.getValue().onDataNotAvailable();
        if(mMockTasksView.isActive()){
            verify(mMockTasksView).showLoadingTasksError();
        }
    }

    @Test
    public void loadAllTasksTest(){
        mTasksPresenter.setFiltering(TasksFilterType.ALL_TASKS);
        mTasksPresenter.loadTasks(true);

        verify(mMockTasksView).setLoadingIndicator(true);
        verify(mMockTasksRepository).refreshTasks();
        verify(mMockTasksRepository).getTasks(mLoadTaskCallbackCaptor.capture());
        mLoadTaskCallbackCaptor.getValue().onTasksLoaded(TASKS);

        verify(mMockTasksView).setLoadingIndicator(false);
        verify(mMockTasksView).showTasks(mTaskListCaptor.capture());
        Assert.assertTrue(TASKS.size() == mTaskListCaptor.getValue().size());
    }

    @Test
    public void loadActiveTasksTest(){
        mTasksPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
        mTasksPresenter.loadTasks(false);

        verify(mMockTasksView).setLoadingIndicator(true);
        verify(mMockTasksRepository).getTasks(mLoadTaskCallbackCaptor.capture());
        mLoadTaskCallbackCaptor.getValue().onTasksLoaded(TASKS);

        verify(mMockTasksView).setLoadingIndicator(false);
        verify(mMockTasksView).showTasks(mTaskListCaptor.capture());
        Assert.assertTrue(mTaskListCaptor.getValue().size() == 1);
    }

    @Test
    public void loadCompletedTasksTest(){
        mTasksPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
        mTasksPresenter.loadTasks(false);

        verify(mMockTasksView).setLoadingIndicator(true);
        verify(mMockTasksRepository).getTasks(mLoadTaskCallbackCaptor.capture());
        mLoadTaskCallbackCaptor.getValue().onTasksLoaded(TASKS);

        verify(mMockTasksView).setLoadingIndicator(false);
        verify(mMockTasksView).showTasks(mTaskListCaptor.capture());
        Assert.assertTrue(mTaskListCaptor.getValue().size() == 2);
    }

    @Test
    public void addNewTaskTest(){
        mTasksPresenter.addNewTask();
        verify(mMockTasksView).showAddTask();
    }

    @Test
    public void openTaskDetailsTest(){
        mTasksPresenter.openTaskDetails(mTestTask);
        Assert.assertNotNull(mTestTask);
        verify(mMockTasksView).showTaskDetailsUi(mTestTask.getId());
    }

    @Test
    public void completeTaskTest(){
        mTasksPresenter.completeTask(mTestTask);
        Assert.assertNotNull(mTestTask);
        verify(mMockTasksRepository).completeTask(mTestTask);
        verify(mMockTasksView).showTaskMarkedComplete();
    }

    @Test
    public void activateTaskTest(){
        mTasksPresenter.activateTask(mTestTask);
        Assert.assertNotNull(mTestTask);
        verify(mMockTasksRepository).activateTask(mTestTask);
        verify(mMockTasksView).showTaskMarkedActive();
    }

    @Test
    public void clearCompletedTasks(){
        mTasksPresenter.clearCompletedTasks();
        verify(mMockTasksRepository).clearCompletedTasks();
        verify(mMockTasksView).showCompletedTasksCleared();
    }

}
