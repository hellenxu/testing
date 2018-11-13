package ca.six.unittestapp.kt

import io.reactivex.Flowable
import io.reactivex.exceptions.MissingBackpressureException
import io.reactivex.observers.TestObserver
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    //failed test: OnErrorNotImplementedException
//    @Test
//    fun getItems() {
//        val oneModel = OneModel()
//        oneModel.fetch()
//                .subscribe {
//                    assertEquals(0, it.size)
//                }
//    }

    @Test
    fun getItems() {
        val items = ArrayList<OneItem>()
        val oneModel = OneModel()
        oneModel.fetch()
                .subscribe {
                    items.addAll(it)
                }

        assertEquals(11, items.size)
    }

    @Test
    fun observerTest() {
        val testObserver = TestObserver.create<Int>()
        testObserver.onNext(1)
        testObserver.onNext(3)
        testObserver.onNext(7)

        testObserver.assertValues(1, 3, 7)
        testObserver.assertNotComplete()

        testObserver.onComplete()
        testObserver.assertComplete()

    }

    @Test
    fun testSubscriber() {
        val testSubscriber = TestSubscriber.create<String>()
        Flowable.just("!", "432", "erw", "wrou", " mm")
                .subscribe(testSubscriber)

        assertTrue(testSubscriber.hasSubscription())
        testSubscriber.assertValueCount(5)
        testSubscriber.assertNever("test")
        testSubscriber.assertValues("!", "432", "erw", "wrou", " mm")
        testSubscriber.assertComplete()
    }

    @Test
    fun testBuffer() {
        val testSubscriber = TestSubscriber.create<List<String>>()
        Flowable.just("1", "3", "5", "7")
                .buffer(2)
                .subscribe(testSubscriber)

        assertTrue(testSubscriber.hasSubscription())
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValues(arrayListOf("1", "3"), arrayListOf("5", "7"))
        testSubscriber.assertTerminated()
    }
}