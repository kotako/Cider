package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers

open class TimelineViewModel(private val timelineView: TimelineFragmentContract) {

    init {
        setTimeline()
    }

    open fun setTimeline() {
        timelineView.showProgressBar()
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, null, null, null, null)
                .map { t -> t.map { tweet -> Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() }))
    }
}