package info.kotlin.kotako.cider.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentTimelineBinding
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import info.kotlin.kotako.cider.view.adapter.TimelineRecyclerViewAdapter
import info.kotlin.kotako.cider.view.listener.RecyclerScrollListener
import info.kotlin.kotako.cider.viewmodel.TimelineViewModel
import info.kotlin.kotako.cider.viewmodel.MentionViewModel

class TimelineFragment : Fragment(), TimelineFragmentContract {

    private var tweetList = ArrayList<Tweet>()
    private var binding: FragmentTimelineBinding? = null

    companion object {
        val SAVED_TWEET_LIST_KEY = "tweetList"
        fun newInstance(): Fragment = TimelineFragment()
        fun newInstance(bundle: Bundle): Fragment = TimelineFragment().apply { arguments = bundle }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false)
        binding?.viewModel = when {
            arguments == null -> TimelineViewModel(this)
            arguments[PagerAdapter.PAGE_TAG] == PagerAdapter.MENTION -> MentionViewModel(this)
            else -> TimelineViewModel(this)
        }

//      RecyclerViewにAdapter, LayoutManager, ScrollListener, 仕切り線をセット
        binding?.recyclerViewTimeline?.adapter = TimelineRecyclerViewAdapter(context, tweetList)
        binding?.recyclerViewTimeline?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewTimeline?.addOnScrollListener(RecyclerScrollListener({ binding?.viewModel?.loadMore(tweetList.last().id) }))
        binding?.recyclerViewTimeline?.addItemDecoration(DividerItemDecoration(binding?.recyclerViewTimeline?.context, LinearLayoutManager(context).orientation))
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        if (TwitterCore.getInstance().sessionManager?.activeSession == null) return
        if (tweetList.isEmpty()) binding?.viewModel?.setTimeline()
        binding?.viewModel?.startStream()
    }

    override fun onStop() {
        super.onStop()
        binding?.viewModel?.unSubscribe()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { tweetList.addAll(it.getSerializable(SAVED_TWEET_LIST_KEY) as ArrayList<Tweet>) }
        binding?.recyclerViewTimeline?.adapter?.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(SAVED_TWEET_LIST_KEY, tweetList)
    }

    //  ----implements TimelineFragmentContract----
    override fun startProfileActivity() = ProfileActivity.start(context)

    override fun addTweet(tweet: Tweet) {
        tweetList.add(tweet)
        binding?.recyclerViewTimeline?.adapter?.notifyItemInserted(tweetList.size)
    }

    override fun addTweetList(tweet: List<Tweet>) {
        tweetList.addAll(tweet)
        activity.runOnUiThread { binding?.recyclerViewTimeline?.adapter?.notifyItemRangeInserted(tweetList.size - tweet.size, tweet.size) }
    }

    override fun clearTweetList() {
        binding?.layoutRefresh?.isRefreshing = false
        tweetList.clear()
        binding?.recyclerViewTimeline?.adapter?.notifyDataSetChanged()
    }

    override fun showSnackBar(msg: String) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
    }

    override fun showProgressBar() {
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.progressBar?.show()
    }

    override fun hideProgressBar() {
        activity.runOnUiThread {
            binding?.progressBar?.hide()
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }
}
