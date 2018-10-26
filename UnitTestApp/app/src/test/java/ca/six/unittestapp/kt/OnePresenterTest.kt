package ca.six.unittestapp.kt

import io.reactivex.Flowable
import io.reactivex.exceptions.MissingBackpressureException
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.TimeUnit

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-10-25.
 */

class OnePresenterTest {
    private lateinit var model: OneModel
    private lateinit var view: OneView
    private lateinit var presenter: OnePresenter
    private val items = ArrayList<OneItem>()
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        model = Mockito.mock(OneModel::class.java)
        view = Mockito.mock(OneView::class.java)
        presenter = OnePresenter(model, view, testScheduler, testScheduler)

        for (i in 0..5) {
            items.add(OneItem(i, "brand: $i"))
        }
    }

    @Test
    fun loadItems() {
        Mockito.doReturn(Flowable.just(items).toObservable()).`when`(model).fetch()
        presenter.loadItems()
        testScheduler.triggerActions() //simulate items emmit
        Mockito.verify(model).fetch()
        Mockito.verify(view).updateView(items)
    }

    //rx2.0 what's new in testing
    @Test
    fun newFeatures() {
        Flowable.range(1, 3)
                .test()
                .assertResult(1, 2, 3)
    }

    @Test
    fun requestMore() {
        Flowable.range(5, 10)
                .test(0)
                .assertNoValues()
                .requestMore(1)
                .assertValue(5)
                .requestMore(2)
                .assertValues(5, 6, 7)
    }

    @Test
    fun assertException() {
        val publishProcessor = PublishProcessor.create<Int>()
        val testSubscriber = publishProcessor.test(0)
        testSubscriber.request(1)
        publishProcessor.onNext(1)
        publishProcessor.onNext(2)

        testSubscriber.assertFailure(MissingBackpressureException::class.java, 1)
    }

    @Test
    fun asynchronousSource() {
        Flowable.just(1)
                .subscribeOn(Schedulers.single())
                .test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(1)
    }
}