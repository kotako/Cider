package info.kotlin.kotako.cider.contract

interface TimelineViewModelContract {
    fun setTimeline()
    fun loadMore(maxId: Long)
    fun onRefresh()
}