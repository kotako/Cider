package info.kotlin.kotako.cider.viewmodel

import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.contract.TimelineViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListTimelineViewModel(val timelineView: TimelineFragmentContract, val listIdStr: String) : TimelineViewModelContract {

    private var disposable = CompositeDisposable()
    private var position = 0L

    override fun start() {
        if (timelineView.tweetListSize() < 1) setTimeline()
    }

    override fun stop() {
        position = 0L
        disposable.dispose()
    }

    override fun setTimeline() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
    }

    override fun startStream() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMore(maxId: Long) {
        if (disposable.isDisposed) disposable = CompositeDisposable()
    }

    override fun onRefresh() {
        timelineView.clearTweetList()
        position = 0L
        setTimeline()
    }
}