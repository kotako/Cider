package info.kotlin.kotako.cider.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentTimelineBinding
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import info.kotlin.kotako.cider.view.adapter.ProfilePagerAdapter
import info.kotlin.kotako.cider.view.adapter.TimelineRecyclerViewAdapter
import info.kotlin.kotako.cider.view.listener.RecyclerScrollListener
import info.kotlin.kotako.cider.viewmodel.*

class TimelineFragment : Fragment(), TimelineFragmentContract {

    private var tweetList = ArrayList<Tweet>()
    private var binding: FragmentTimelineBinding? = null

    companion object {
        val SAVED_TWEET_LIST_KEY = "tweetList"
        fun newInstance(): Fragment = TimelineFragment()
        fun newInstance(bundle: Bundle?): Fragment = TimelineFragment().apply { bundle?.let { arguments = it } }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false)

//          Bundleの値によってセットするviewModelを変更する
            binding?.viewModel = when {
                arguments == null -> TimelineViewModel(this)
                arguments.containsKey(TabManager.TARGET) -> {
                    when (arguments.getString(TabManager.TARGET)) {
                        TabManager.TIMELINE -> TimelineViewModel(this)
                        TabManager.MENTION -> MentionViewModel(this)
                        TabManager.USERLIST -> ListTimelineViewModel(this, arguments.getString(TabManager.TARGET_ID))
                        TabManager.SEARCH -> SearchTimelineViewModel(this, arguments.getString(TabManager.TARGET_ID))
                        else -> TimelineViewModel(this)
                    }
                }
                arguments.containsKey(ProfilePagerAdapter.USER_ID) -> ProfileTimelineViewModel(this, arguments[ProfilePagerAdapter.USER_ID] as Long)
                else -> TimelineViewModel(this)
            }
        }

        binding?.layoutRefresh?.setOnRefreshListener { binding?.viewModel?.onRefresh() }
//      RecyclerViewにAdapter, LayoutManager, ScrollListener, 仕切り線をセットする
        binding?.recyclerViewTimeline?.apply {
            adapter = TimelineRecyclerViewAdapter(context, tweetList)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(RecyclerScrollListener({ binding?.viewModel?.loadMore(tweetList.last().id) }))
            invalidateItemDecorations()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
        }
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        if (TwitterCore.getInstance().sessionManager?.activeSession == null) return
        binding?.viewModel?.start()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { tweetList.addAll(it.getSerializable(SAVED_TWEET_LIST_KEY) as ArrayList<Tweet>) }
        binding?.recyclerViewTimeline?.adapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.viewModel?.stop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(SAVED_TWEET_LIST_KEY, tweetList)
    }

    //  ----implements TimelineFragmentContract----
    override fun startProfileActivity() = ProfileActivity.start(context)

    override fun addTweet(tweet: Tweet) {
        tweetList.add(0, tweet)
        activity.runOnUiThread {
            binding?.recyclerViewTimeline?.apply {
                adapter.notifyItemInserted(0)
                if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 0) smoothScrollToPosition(0)
            }
        }
    }

    override fun addTweetList(tweet: List<Tweet>) {
        tweetList.addAll(tweet)
        activity.runOnUiThread { binding?.recyclerViewTimeline?.adapter?.notifyItemRangeChanged(tweetList.size - tweet.size, tweet.size) }
    }

    override fun tweetListSize(): Int = tweetList.size

    override fun clearTweetList() {
        tweetList.clear()
        binding?.recyclerViewTimeline?.adapter?.notifyDataSetChanged()
    }

    override fun showSnackBar(msg: String) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
    }

    override fun showProgressBar() {
        activity.runOnUiThread { binding?.layoutRefresh?.isRefreshing = true }
    }

    override fun hideProgressBar() {
        activity.runOnUiThread { binding?.layoutRefresh?.isRefreshing = false }
    }
}
