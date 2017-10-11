package info.kotlin.kotako.cider.rx

import rx.Observer

class DefaultObserver<T>(private val next: (T) -> Unit = {},
                         private val error: (Throwable) -> Unit = {},
                         private val completed: () -> Unit = {}) : Observer<T> {
    override fun onNext(t: T): Unit = next(t)
    override fun onError(e: Throwable): Unit = error(e)
    override fun onCompleted(): Unit = completed()
}