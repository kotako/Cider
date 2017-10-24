package info.kotlin.kotako.cider.rx

import io.reactivex.observers.DisposableObserver

class DefaultObserver<T>(private val next: (T) -> Unit = {},
                         private val error: (Throwable) -> Unit = {},
                         private val completed: () -> Unit = {}) : DisposableObserver<T>() {
    override fun onNext(t: T) = next(t)
    override fun onError(e: Throwable) = error(e)
    override fun onComplete() = completed()
}