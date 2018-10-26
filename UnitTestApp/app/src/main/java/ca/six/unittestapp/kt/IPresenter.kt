package ca.six.unittestapp.kt

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface IPresenter {
    fun loadItems()
}

interface IView {
    fun updateView(items: List<Any>)
}

interface IModel {
    fun fetch(): Observable<List<OneItem>>
}

data class OneItem(val id: Int, val brand: String)

class OnePresenter(val model: OneModel, val view: OneView): IPresenter {
    override fun loadItems() {
         model.fetch()
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe {
                    view.updateView(it)
                 }
                 .disposedBy(manager = CompositeDisposable())
    }

}

open class OneView: IView {
    init {
        val model = OneModel()
        val presenter = OnePresenter(model, this)
        presenter.loadItems()
    }

    override fun updateView(items: List<Any>) {
        println("update items: ${items.size}")
    }
}

open class OneModel: IModel {
    override fun fetch(): Observable<List<OneItem>> {
        println("xxl-sent request, fetching...")

        return Maybe.create<List<OneItem>> {emitter ->
            val itemList = ArrayList<OneItem>()
            for(i in 0..10) {
                val item = OneItem(i, "brand$i")
                itemList.add(item)
            }
            emitter.onSuccess(itemList)
        }.toObservable()
    }

}