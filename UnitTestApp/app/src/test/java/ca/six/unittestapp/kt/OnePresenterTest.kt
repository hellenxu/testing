package ca.six.unittestapp.kt

import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-10-25.
 */

class OnePresenterTest {
    private lateinit var model: OneModel
    private lateinit var view: OneView
    private lateinit var presenter: OnePresenter
    private val items = ArrayList<OneItem>()

    @Before
    fun setUp() {
        model = Mockito.mock(OneModel::class.java)
        view = Mockito.mock(OneView::class.java)
        presenter = OnePresenter(model, view)

        for (i in 0..5) {
            items.add(OneItem(i, "brand: $i"))
        }
    }

    @Test
    fun loadItems() {
        Mockito.doReturn(Flowable.just(items).toObservable()).`when`(model).fetch()
        presenter.loadItems()
        Mockito.verify(model).fetch()
        Mockito.verify(view).updateView(items)
    }
}