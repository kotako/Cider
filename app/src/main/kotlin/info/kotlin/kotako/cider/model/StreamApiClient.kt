package info.kotlin.kotako.cider.model

import rx.Observable
import twitter4j.*

class StreamApiClient {
    companion object {
        val statusObservable = Observable.create<Status> { subscriber ->
            TwitterStreamFactory(AccountManager.currentConfig()).instance
                    .run {
                        onStatus { subscriber.onNext(it) }
                        onException { subscriber.onError(it) }
                        user()
                    }
        }.share()
    }
}