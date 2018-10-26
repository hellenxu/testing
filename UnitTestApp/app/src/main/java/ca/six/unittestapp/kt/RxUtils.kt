package ca.six.unittestapp.kt

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-10-25.
 */
fun Disposable.disposedBy(manager: CompositeDisposable){
    manager.add(this)
}