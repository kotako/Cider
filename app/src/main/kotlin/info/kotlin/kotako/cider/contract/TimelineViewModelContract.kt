package info.kotlin.kotako.cider.contract

interface TimelineViewModelContract {
    fun setTimeline()
    fun loadMore(sinceId: Long)
}