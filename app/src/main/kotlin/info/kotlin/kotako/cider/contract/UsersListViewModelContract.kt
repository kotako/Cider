package info.kotlin.kotako.cider.contract


interface UsersListViewModelContract {
    fun setUsers()
    fun loadMore(maxId: Long)
    fun onRefresh()
    fun start()
    fun stop()
}
