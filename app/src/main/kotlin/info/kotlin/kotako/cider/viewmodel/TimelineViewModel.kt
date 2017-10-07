package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.StreamApiClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class TimelineViewModel(private val timelineView: TimelineFragmentContract) : TimelineViewModelContract {

    private var subscription = CompositeSubscription()

    override fun startStream() {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        subscription.add(
                StreamApiClient.statusObservable
                        .map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) }
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(DefaultObserver<Tweet>(
                                next = { timelineView.addTweet(it) },
                                error = { Log.d("TimelineViewModel", it.localizedMessage) }
                        )))
    }

    override fun start() {
        if (!subscription.hasSubscriptions()) {
            setTimeline()
            startStream()
        }
    }

    override fun stop() = subscription.unsubscribe()

    override fun setTimeline() {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        timelineView.showProgressBar()
        subscription.add(APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, null, null, null, null)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { throwable ->
                            timelineView.hideProgressBar()
                            timelineView.showSnackBar(throwable.localizedMessage)
                        },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun loadMore(maxId: Long) {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        timelineView.showProgressBar()
        subscription.add(APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, maxId, null, null, null)
                .map { t -> t.drop(1) }
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { throwable ->
                            timelineView.hideProgressBar()
                            timelineView.showSnackBar(throwable.localizedMessage)
                        },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }
}