package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.Tweet
import rx.schedulers.Schedulers

open class TimelineViewModel(private val timelineView: TimelineFragmentContract) {

    init {
        setTimeline()
    }

    open fun setTimeline() {
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, null, null, null, null)
                .map { t -> t.map { tweet -> Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { e -> e.printStackTrace() },
                        completed = { Log.d("LoadHomeTimeline", "finish") }))
    }
}