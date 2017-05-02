package ca.six.unittestapp.todo.statistics;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import ca.six.unittestapp.todo.data.Task;
import ca.six.unittestapp.todo.data.source.TasksDataSource;
import ca.six.unittestapp.todo.data.source.TasksRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-05-01.
 */

public class StatisticsPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TasksRepository mMockTasksRepository;
    @Mock
    private StatisticsContract.View mMockStatView;

    private StatisticsPresenter mStatPresenter;
    private ArgumentCaptor<TasksDataSource.LoadTasksCallback> mLoadTaskCaptor;

    private final List<Task> TASKS
            = Lists.newArrayList(new Task("Title1", "Description1"),
            new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));

    @Before
    public void setUp(){
        mStatPresenter = new StatisticsPresenter(mMockTasksRepository, mMockStatView);
        mLoadTaskCaptor = ArgumentCaptor.forClass(TasksDataSource.LoadTasksCallback.class);

        when(mMockStatView.isActive()).thenReturn(true);
    }

    @Test
    public void cfTest(){
        verify(mMockStatView).setPresenter(mStatPresenter);
    }

    @Test
    public void loadValidStat(){
        mStatPresenter.start();

        verify(mMockStatView).setProgressIndicator(true);
        verify(mMockTasksRepository).getTasks(mLoadTaskCaptor.capture());
        mLoadTaskCaptor.getValue().onTasksLoaded(TASKS);
        verify(mMockStatView).setProgressIndicator(false);
        verify(mMockStatView).showStatistics(1, 2);
    }

    @Test
    public void loadStatError(){
        mStatPresenter.start();

        verify(mMockStatView).setProgressIndicator(true);
        verify(mMockTasksRepository).getTasks(mLoadTaskCaptor.capture());
        mLoadTaskCaptor.getValue().onDataNotAvailable();
        verify(mMockStatView).showLoadingStatisticsError();
    }

}
