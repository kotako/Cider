package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers

class TimelineViewModel(private val timelineView: TimelineFragmentContract) : TimelineViewModelContract {

    init {
        TwitterCore.getInstance().sessionManager?.activeSession?.let {
            setTimeline()
        }
    }

    override fun setTimeline() {
        timelineView.showProgressBar()
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, null, null, null, null)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, tweet.user) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { throwable ->
                            timelineView.hideProgressBar()
                            timelineView.showSnackBar(throwable.localizedMessage)
                        },
                        completed = { timelineView.hideProgressBar() }))
    }

    override fun loadMore(maxId: Long) {
        timelineView.showProgressBar()
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, maxId, null, null, null)
                .map { t -> t.drop(1) }
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, tweet.user) else Tweet(tweet)  } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { throwable ->
                            timelineView.hideProgressBar()
                            timelineView.showSnackBar(throwable.localizedMessage)
                        },
                        completed = { timelineView.hideProgressBar() }))
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }
}