package info.kotlin.kotako.cider.view.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerScrollListener(private val onLoadMore: () -> Unit = {}) : RecyclerView.OnScrollListener() {

    var loading = false
    var visibleItemCount = 0    // 現在表示されているviewの個数
    var totalItemCount = 0      // バインドされたadapterの持つ個数
    var previousTotalItemCount = 0
    var firstVisibleItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = recyclerView.layoutManager.itemCount
        firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        //Log.d("scrollcount", visibleItemCount.toString()+" "+totalItemCount.toString()+ " "+firstVisibleItem.toString())

        if (loading) {
            if (totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem + 5) {
            previousTotalItemCount = totalItemCount
            loading = true
            onLoadMore()
        }
    }
}