package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class ProfileTimelineViewModel(val timelineView: TimelineFragmentContract, val userId: Long) : TimelineViewModelContract {

    private var subscription = CompositeSubscription()

    override fun setTimeline() {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        timelineView.showProgressBar()
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .userTimeline(userId, null, null, 50, null, null, null, true)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() }
                ))
    }

    override fun loadMore(maxId: Long) {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        timelineView.showProgressBar()
        subscription.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .userTimeline(userId, null, null, 50, maxId, null, null, null)
                .map { t -> t.drop(1) }
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }

    override fun start() =  if (timelineView.tweetListSize() < 1) setTimeline() else {}

    override fun stop() = subscription.unsubscribe()

    override fun startStream() {}
}