package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.StreamApiClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchTimelineViewModel(private val timelineView: TimelineFragmentContract, private val query: String) : TimelineViewModelContract {

    private var disposable = CompositeDisposable().apply { dispose() }
    private var position = 0L

    override fun start() {
        // if (disposable.isDisposed) startStream()
        if (timelineView.tweetListSize() < 1) setTimeline()
    }

    override fun stop() {
        disposable.dispose()
        position = 0L
    }

    override fun setTimeline() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .searchTimeline(query, null, null, null, null, 50, null, null, null, null)
                .map { t -> t.statuses.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { Log.d("dev", it.localizedMessage) },
                        completed = { timelineView.hideProgressBar() }
                )))
    }

    override fun loadMore(maxId: Long) {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .searchTimeline(query, null, null, null, null, 50, null, null, maxId, null)
                .map { t -> t.statuses.drop(1) }
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun startStream() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        disposable.add(
                StreamApiClient.searchStatusObservable(query)
                        .map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) }
                        .subscribeOn(Schedulers.newThread())
                        .subscribeWith(DefaultObserver<Tweet>(
                                next = { timelineView.addTweet(it) },
                                error = { Log.d("TimelineViewModel", it.localizedMessage) }
                        )))
    }

    override fun onRefresh() {
        position = 0L
        setTimeline()
    }
}