package ca.six.unittestapp.todo.data.source;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import ca.six.unittestapp.todo.data.Task;

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

    private final List<Task> TASKS
            = Lists.newArrayList(new Task("Title1", "Description1"),
            new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));

    @Before
    public void setUp(){
        mTasksRepository = TasksRepository.getInstance(mMockRemoteDataSource, mMockLocalDataSource);
        mLoadTasksCbCaptor = ArgumentCaptor.forClass(TasksDataSource.LoadTasksCallback.class);
    }

    @Test
    public void getTasksFromLocalDataSource(){
        TasksDataSource.LoadTasksCallback loadCallback = Mockito.mock(TasksDataSource.LoadTasksCallback.class);
        mTasksRepository.getTasks(loadCallback);

        verify(mMockLocalDataSource).getTasks(mLoadTasksCbCaptor.capture());
        mLoadTasksCbCaptor.getValue().onTasksLoaded(TASKS);
    }

}
