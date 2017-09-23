package ca.six.unittestapp.todo.data.source;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ca.six.unittestapp.todo.data.Task;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-05-02.
 */

public class TasksRepositoryTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TasksDataSource mMockRemoteDataSource;
    @Mock
    private TasksDataSource mMockLocalDataSource;

    private TasksRepository mTasksRepository;
    private ArgumentCaptor<TasksDataSource.LoadTasksCallback> mLoadTasksCbCaptor;
    private ArgumentCaptor<TasksDataSource.GetTaskCallback> mGetTaskCbCaptor;

    private static final List<Task> TASKS
            = Lists.newArrayList(new Task("Title1", "Description1"),
            new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));

    private static final Task TEST_TASK_UNDONE = new Task("Title4", "Description4", false);

    private static final Task TEST_TASK_DONE = new Task("Title5", "Description5", true);

    @Before
    public void setUp() {
        mTasksRepository = TasksRepository.getInstance(mMockRemoteDataSource, mMockLocalDataSource);
        mLoadTasksCbCaptor = ArgumentCaptor.forClass(TasksDataSource.LoadTasksCallback.class);
        mGetTaskCbCaptor = ArgumentCaptor.forClass(TasksDataSource.GetTaskCallback.class);
    }

    @After
    public void tearDown() {
        TasksRepository.destroyInstance();
    }

    @Test
    public void getTasksFromCacheSuccess() {
        TasksDataSource.LoadTasksCallback loadCallback = Mockito.mock(TasksDataSource.LoadTasksCallback.class);
        mTasksRepository.mCacheIsDirty = false;
        Map<String, Task> cachedTasks = new LinkedHashMap<>();
        for (Task task : TASKS) {
            cachedTasks.put(task.getId(), task);
        }
        mTasksRepository.mCachedTasks = cachedTasks;

        mTasksRepository.getTasks(loadCallback);
        verify(loadCallback).onTasksLoaded(TASKS);
    }

    @Test
    public void getTasksFromLocalDataSourceSuccess() {
        TasksDataSource.LoadTasksCallback loadCallback = Mockito.mock(TasksDataSource.LoadTasksCallback.class);

        mTasksRepository.getTasks(loadCallback);
        verify(mMockLocalDataSource).getTasks(mLoadTasksCbCaptor.capture());
        mLoadTasksCbCaptor.getValue().onTasksLoaded(TASKS);
        verify(loadCallback).onTasksLoaded(TASKS);
    }

    @Test
    public void getTasksFromLocalDataSourceDataNotAvailable() {
        TasksDataSource.LoadTasksCallback loadCallback = Mockito.mock(TasksDataSource.LoadTasksCallback.class);

        mTasksRepository.getTasks(loadCallback);
        verify(mMockLocalDataSource).getTasks(mLoadTasksCbCaptor.capture());
        mLoadTasksCbCaptor.getValue().onDataNotAvailable();
        verify(mMockRemoteDataSource).getTasks(mLoadTasksCbCaptor.capture());

        mLoadTasksCbCaptor.getValue().onTasksLoaded(TASKS);
        verify(loadCallback).onTasksLoaded(TASKS);
        mLoadTasksCbCaptor.getValue().onDataNotAvailable();
        verify(loadCallback).onDataNotAvailable();
    }

    @Test
    public void getTasksFromRemoteDataSourceSuccess() {
        TasksDataSource.LoadTasksCallback loadCallback = Mockito.mock(TasksDataSource.LoadTasksCallback.class);

        mTasksRepository.mCacheIsDirty = true;
        mTasksRepository.getTasks(loadCallback);
        verify(mMockRemoteDataSource).getTasks(mLoadTasksCbCaptor.capture());
        mLoadTasksCbCaptor.getValue().onTasksLoaded(TASKS);
        verify(loadCallback).onTasksLoaded(TASKS);
    }

    @Test
    public void getTaskFromLocalDataSourceWhenNoCachedTasks(){
        TasksDataSource.GetTaskCallback getCallback = Mockito.mock(TasksDataSource.GetTaskCallback.class);
        mTasksRepository.getTask(TEST_TASK_UNDONE.getId(), getCallback);

        verify(mMockLocalDataSource).getTask(eq(TEST_TASK_UNDONE.getId()), mGetTaskCbCaptor.capture());
        mGetTaskCbCaptor.getValue().onTaskLoaded(TEST_TASK_UNDONE);
        verify(getCallback).onTaskLoaded(TEST_TASK_UNDONE);
    }

    @Test
    public void getTaskFromRemoteDataSourceWhenNoCachedTasks() {
        TasksDataSource.GetTaskCallback getCallback = Mockito.mock(TasksDataSource.GetTaskCallback.class);
        mTasksRepository.getTask(TEST_TASK_UNDONE.getId(), getCallback);

        verify(mMockLocalDataSource).getTask(eq(TEST_TASK_UNDONE.getId()), mGetTaskCbCaptor.capture());
        mGetTaskCbCaptor.getValue().onDataNotAvailable();
        verify(mMockRemoteDataSource).getTask(eq(TEST_TASK_UNDONE.getId()), mGetTaskCbCaptor.capture());
        mGetTaskCbCaptor.getValue().onTaskLoaded(TEST_TASK_UNDONE);
        verify(getCallback).onTaskLoaded(TEST_TASK_UNDONE);
        mGetTaskCbCaptor.getValue().onDataNotAvailable();
        verify(getCallback).onDataNotAvailable();
    }

    @Test
    public void getTaskFromCachedTasks(){
        mTasksRepository.saveTask(TEST_TASK_UNDONE);

        TasksDataSource.GetTaskCallback getCallback = Mockito.mock(TasksDataSource.GetTaskCallback.class);
        mTasksRepository.getTask(TEST_TASK_UNDONE.getId(), getCallback);
        verify(getCallback).onTaskLoaded(TEST_TASK_UNDONE);
    }

    @Test
    public void saveTaskSuccess() {
        mTasksRepository.saveTask(TEST_TASK_UNDONE);

        verify(mMockRemoteDataSource).saveTask(TEST_TASK_UNDONE);
        verify(mMockLocalDataSource).saveTask(TEST_TASK_UNDONE);
        assertNotNull(mTasksRepository.mCachedTasks);
        assertFalse(mTasksRepository.mCachedTasks.isEmpty());
        assertTrue(mTasksRepository.mCachedTasks.containsValue(TEST_TASK_UNDONE));
    }

    private void assertCompleteTask(){
        verify(mMockRemoteDataSource).completeTask(TEST_TASK_UNDONE);
        verify(mMockLocalDataSource).completeTask(TEST_TASK_UNDONE);

        assertNotNull(mTasksRepository.mCachedTasks);
        assertFalse(mTasksRepository.mCachedTasks.isEmpty());
        Task doneTask = mTasksRepository.mCachedTasks.get(TEST_TASK_UNDONE.getId());
        assertTrue(doneTask.isCompleted());
    }

    @Test
    public void completeTaskWithTask() {
        mTasksRepository.completeTask(TEST_TASK_UNDONE);

        assertCompleteTask();
    }

    @Test
    public void completeTaskWithTaskId() {
        mTasksRepository.saveTask(TEST_TASK_UNDONE);
        mTasksRepository.completeTask(TEST_TASK_UNDONE.getId());

        assertCompleteTask();
    }

    private void assertActivateTask(){
        verify(mMockRemoteDataSource).activateTask(TEST_TASK_DONE);
        verify(mMockLocalDataSource).activateTask(TEST_TASK_DONE);
        assertNotNull(mTasksRepository.mCachedTasks);
        assertFalse(mTasksRepository.mCachedTasks.isEmpty());
        Task activatedTask = mTasksRepository.mCachedTasks.get(TEST_TASK_DONE.getId());
        assertTrue(activatedTask.isActive());
    }

    @Test
    public void activateTaskWithTask() {
        mTasksRepository.activateTask(TEST_TASK_DONE);

        assertActivateTask();
    }

    @Test
    public void activateTaskWithTaskId() {
        mTasksRepository.saveTask(TEST_TASK_DONE);
        mTasksRepository.activateTask(TEST_TASK_DONE.getId());

        assertActivateTask();
    }

    @Test
    public void clearCompletedTasksWhenHasCachedTasks(){
        mTasksRepository.saveTask(TEST_TASK_DONE);
        assertTrue(mTasksRepository.mCachedTasks.containsValue(TEST_TASK_DONE));

        mTasksRepository.clearCompletedTasks();

        assertClearCompletedTasks();
    }

    private void assertClearCompletedTasks(){
        verify(mMockRemoteDataSource).clearCompletedTasks();
        verify(mMockLocalDataSource).clearCompletedTasks();
        assertFalse(mTasksRepository.mCachedTasks.containsValue(TEST_TASK_DONE));
    }

    @Test
    public void clearCompletedTasksWithNoCachedTasks() {
        mTasksRepository.clearCompletedTasks();

        assertClearCompletedTasks();
    }

    @Test
    public void refreshTasks(){
        assertFalse(mTasksRepository.mCacheIsDirty);
        mTasksRepository.refreshTasks();
        assertTrue(mTasksRepository.mCacheIsDirty);
    }

    @Test
    public void deleteAllTasksSuccess(){
        mTasksRepository.deleteAllTasks();

        verify(mMockRemoteDataSource).deleteAllTasks();
        verify(mMockLocalDataSource).deleteAllTasks();
        assertTrue(mTasksRepository.mCachedTasks.isEmpty());
    }

    @Test
    public void deleteTaskSuccess(){
        mTasksRepository.saveTask(TEST_TASK_UNDONE);
        assertTrue(mTasksRepository.mCachedTasks.containsValue(TEST_TASK_UNDONE));

        mTasksRepository.deleteTask(TEST_TASK_UNDONE.getId());

        verify(mMockRemoteDataSource).deleteTask(TEST_TASK_UNDONE.getId());
        verify(mMockLocalDataSource).deleteTask(TEST_TASK_UNDONE.getId());
        assertNull(mTasksRepository.mCachedTasks.get(TEST_TASK_UNDONE.getId()));
    }
}
