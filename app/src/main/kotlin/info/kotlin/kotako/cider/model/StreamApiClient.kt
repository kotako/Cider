package info.kotlin.kotako.cider.model

import io.reactivex.Observable
import twitter4j.*
import java.lang.Exception

object StreamApiClient {
    val statusObservable = Observable.create<Any> { subscriber ->
        TwitterStreamFactory(AccountManager.currentConfig()).instance
                .apply {
                    StreamListenerWrapper.addStatusListener(this, object : UserStreamAdapter() {
                        override fun onStatus(status: Status) = subscriber.onNext(status)
                        override fun onException(ex: Exception) = subscriber.onError(ex)
                        override fun onDirectMessage(directMessage: DirectMessage) { subscriber.onNext(directMessage) }
                    })
                    user()
                }
    }.share()
}