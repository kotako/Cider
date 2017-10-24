package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileTimelineViewModel(val timelineView: TimelineFragmentContract, val userId: Long) : TimelineViewModelContract {

    private var disposable = CompositeDisposable()

    override fun setTimeline() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .userTimeline(userId, null, null, 50, null, null, null, true)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() }
                ))
    }

    override fun loadMore(maxId: Long) {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .userTimeline(userId, null, null, 50, maxId, null, null, null)
                .map { t -> t.drop(1) }
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { timelineView.hideProgressBar() },
                        completed = { timelineView.hideProgressBar() })))
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }

    override fun start() =  if (timelineView.tweetListSize() < 1) setTimeline() else {}

    override fun stop() = disposable.dispose()

    override fun startStream() {}
}