package info.kotlin.kotako.cider.viewmodel

import android.view.View
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.model.TimelineManager
import info.kotlin.kotako.cider.model.Tweet
import rx.Observer

class TimelineViewModel(val timelineView: TimelineFragmentContract, arg:String? = null) {

    init {
        if (timelineManager.getActiveUserId() == null) timelineView.showSnackBar("はじめに、アカウントを追加しましょう！")
        if (arg == "mention") {
            setMention()
        } else {
            setTimeline()
        }
    }

    companion object {
        val timelineManager = TimelineManager.getInstance()
    }

    fun onIconClicked(view: View) {
//      ウィコンをクリックするとそのユーザのプロフィールを表示
        timelineView.startProfileActivity()
    }

    fun setTimeline() {
        timelineManager.homeTimeline(object : Observer<Tweet> {
            override fun onNext(t: Tweet?) { t?.let { timelineView.addTweet(it) } }
            override fun onError(e: Throwable?) {}
            override fun onCompleted() {}
        })
    }

    fun setMention() {
        timelineManager.mentionsTimeline(object : Observer<Tweet> {
            override fun onNext(t: Tweet?) { t?.let { timelineView.addTweet(it) } }
            override fun onError(e: Throwable?) {}
            override fun onCompleted() {}
        })
    }
}