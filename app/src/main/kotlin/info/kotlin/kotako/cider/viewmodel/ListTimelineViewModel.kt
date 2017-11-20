package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.StreamApiClient
import info.kotlin.kotako.cider.model.entity.ListMembers
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.model.entity.UserList
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import twitter4j.Status

class ListTimelineViewModel(private val timelineView: TimelineFragmentContract, private val listId: Long) : TimelineViewModelContract {

    private var disposable = CompositeDisposable()
    private var memberIdList = ArrayList<Long>()

    override fun start() {
        if (timelineView.tweetListSize() < 1) setTimeline()
        if (memberIdList.size < 1) loadMember(listId)
        startStream()
    }

    override fun stop() {
        disposable.dispose()
    }

    private fun loadMember(listId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .UsersObservable()
                .showUserListMember(listId, null, null, null, 300, null, false, false)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<ListMembers>(
                        next = { memberIdList.addAll(it.users.map { it.id }) },
                        error = { Log.d("dev_list_viewmodel", it.localizedMessage) }
                ))
    }

    override fun setTimeline() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .listTimeline(listId, null, null, null, null, null, 50, null, null)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it); timelineView.hideProgressBar() },
                        error = { Log.d("dev_list_viewmodel", it.localizedMessage); timelineView.hideProgressBar() }
                )))
    }

    override fun startStream() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        disposable.add(StreamApiClient
                .statusObservable
                .filter({ it is Status })
                .filter({ memberIdList.contains((it as Status).user.id) })
                .map { it as Status }
                .map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) }
                .subscribeWith(DefaultObserver<Tweet>(
                        next = { timelineView.addTweet(it) },
                        error = { Log.d("dev_list_streaming", it.localizedMessage) }
                )))
    }

    override fun loadMore(maxId: Long) {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        timelineView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .listTimeline(listId, null, null, null, null, maxId, 50, null, null)
                .map { t -> t.map { tweet -> if (tweet.retweetedStatus != null) Tweet(tweet.retweetedStatus, User(tweet.user)) else Tweet(tweet) } }
                .map { it.drop(1) }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it); timelineView.hideProgressBar() },
                        error = { Log.d("dev_list_viewmodel", it.localizedMessage); timelineView.hideProgressBar() }
                )))
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        setTimeline()
    }
}