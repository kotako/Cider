package info.kotlin.kotako.cider.viewmodel

import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.Tweet
import rx.schedulers.Schedulers

class TimelineViewModel(val timelineView: TimelineFragmentContract) {

    init {
        setTimeline()
    }

    fun onIconClicked(view: View) {
//      ウィコンをクリックするとそのユーザのプロフィールを表示
        timelineView.startProfileActivity()
    }

    private fun setTimeline() {
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .homeTimeline(50, null, null)
                .map { t -> t.map { tweet -> Tweet(tweet) } }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) },
                        error = { e -> e.printStackTrace() }))
    }
}