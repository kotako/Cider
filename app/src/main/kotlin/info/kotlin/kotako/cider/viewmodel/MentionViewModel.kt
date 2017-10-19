package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.model.StreamApiClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MentionViewModel(private val timelineView: TimelineFragmentContract) : TimelineViewModelContract {

    private var subscription = CompositeSubscription()

    override fun startStream() {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        subscription.add(
                StreamApiClient.statusObservable
                        .filter { status -> status.inReplyToUserId == AccountManager.currentAccount()?.userId }
                        .map { status -> Tweet(status) }
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(DefaultObserver<Tweet>(
                                next = { timelineView.addTweet(it) },
                                error = { Log.d("MentionViewModel", it.localizedMessage) }
                        )))
    }

    override fun setTimeline() {
        if (!subscription.hasSubscriptions()) subscription = CompositeSubscription()
        timelineView.showProgressBar()
        subscription.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .mentionTimeline(20, null, null, null, null)
                .map { t -> t.map { tweet -> Tweet(tweet) } }
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
        subscription.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .mentionTimeline(20, null, maxId, null, null)
                .map { t -> t.drop(1) }
                .map { t -> t.map { tweet -> Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { throwable ->
                            timelineView.hideProgressBar()
                            timelineView.showSnackBar(throwable.localizedMessage)
                        },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun start() {
        if (!subscription.hasSubscriptions()) startStream()
        if (timelineView.tweetListSize() < 1) setTimeline()
    }

    override fun stop() = subscription.unsubscribe()

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }
}