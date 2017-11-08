package info.kotlin.kotako.cider.contract

interface DMViewModelContract{
    fun start()
    fun stop()
    fun loadDirectMessages()
    fun loadMore()
    fun refresh()
    fun startStream()
}
