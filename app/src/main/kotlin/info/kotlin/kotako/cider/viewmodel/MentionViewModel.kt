package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.Tweet

class MentionViewModel(val timelineView: TimelineFragmentContract) {

    private fun setTimeline() {
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .TimelineObservable()
                .mentionTimeline(50, null, null, null, null)
                .map { t -> t.map { tweet -> Tweet(tweet) } }
                .subscribe(DefaultObserver<List<Tweet>>(
                        next = { timelineView.addTweetList(it) }))
    }
}