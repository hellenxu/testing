package ca.six.unittestapp.todo.taskdetail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import ca.six.unittestapp.todo.data.Task;
import ca.six.unittestapp.todo.data.source.TasksDataSource;
import ca.six.unittestapp.todo.data.source.TasksRepository;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-04-28.
 */

public class TaskDetailPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TasksRepository mMockTasksRepository;
    @Mock
    private TaskDetailContract.View mMockTaskDetailView;

    private TaskDetailPresenter mTaskDetailPresenter;
    private ArgumentCaptor<TasksDataSource.GetTaskCallback> mGetTaskCbCaptor;

    private static final String TEST_TASK_ID = "014";
    private static final Task TEST_TASK = new Task("title 1", "test task", TEST_TASK_ID);

    @Before
    public void setUp(){
        mTaskDetailPresenter = new TaskDetailPresenter(TEST_TASK_ID, mMockTasksRepository,
                mMockTaskDetailView);
        mGetTaskCbCaptor = ArgumentCaptor.forClass(TasksDataSource.GetTaskCallback.class);
        when(mMockTaskDetailView.isActive()).thenReturn(true);
    }

    @Test
    public void cfTest(){
        assertNotNull(mMockTasksRepository);
        assertNotNull(mMockTaskDetailView);
        verify(mMockTaskDetailView).setPresenter(mTaskDetailPresenter);
    }

    @Test
    public void startWithValidTaskId(){
        mTaskDetailPresenter.start();

        verify(mMockTaskDetailView).setLoadingIndicator(true);
        verify(mMockTasksRepository).getTask(eq(TEST_TASK_ID), mGetTaskCbCaptor.capture());
        mGetTaskCbCaptor.getValue().onTaskLoaded(TEST_TASK);
        verify(mMockTaskDetailView).setLoadingIndicator(false);
        verify(mMockTaskDetailView).showTitle(TEST_TASK.getTitle());
        verify(mMockTaskDetailView).showDescription(TEST_TASK.getDescription());
        verify(mMockTaskDetailView).showCompletionStatus(TEST_TASK.isCompleted());
    }

    @Test
    public void startWithDataNotAvailable(){
        mTaskDetailPresenter.start();

        verify(mMockTaskDetailView).setLoadingIndicator(true);
        verify(mMockTasksRepository).getTask(eq(TEST_TASK_ID), mGetTaskCbCaptor.capture());
        mGetTaskCbCaptor.getValue().onDataNotAvailable();
        verify(mMockTaskDetailView).showMissingTask();
    }

    @Test
    public void editTaskTest(){
        mTaskDetailPresenter.editTask();

        verify(mMockTaskDetailView).showEditTask(TEST_TASK_ID);
    }

    @Test
    public void deleteTaskTest(){
        mTaskDetailPresenter.deleteTask();

        verify(mMockTasksRepository).deleteTask(TEST_TASK_ID);
        verify(mMockTaskDetailView).showTaskDeleted();
    }

    @Test
    public void completeValidTask(){
        mTaskDetailPresenter.completeTask();

        verify(mMockTasksRepository).completeTask(TEST_TASK_ID);
        verify(mMockTaskDetailView).showTaskMarkedComplete();
    }

    @Test
    public void completeEmptyTask(){
        mTaskDetailPresenter = new TaskDetailPresenter(null, mMockTasksRepository, mMockTaskDetailView);
        mTaskDetailPresenter.completeTask();

        verify(mMockTaskDetailView).showMissingTask();
    }

    @Test
    public void activateValidTask(){
        mTaskDetailPresenter.activateTask();

        verify(mMockTasksRepository).activateTask(TEST_TASK_ID);
        verify(mMockTaskDetailView).showTaskMarkedActive();
    }

    @Test
    public void activateInvalidTask(){
        mTaskDetailPresenter = new TaskDetailPresenter(null, mMockTasksRepository, mMockTaskDetailView);
        mTaskDetailPresenter.activateTask();

        verify(mMockTaskDetailView).showMissingTask();
    }
}
