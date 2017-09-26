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
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentTimelineBinding
import info.kotlin.kotako.cider.model.Tweet
import info.kotlin.kotako.cider.view.PagerAdapter
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import info.kotlin.kotako.cider.view.TimelineRecyclerViewAdapter
import info.kotlin.kotako.cider.viewmodel.TimelineViewModel
import info.kotlin.kotako.cider.viewmodel.MentionViewModel

class TimelineFragment : Fragment(), TimelineFragmentContract {

    private val tweetList = ArrayList<Tweet>()
    private var adapter: TimelineRecyclerViewAdapter? = null

    companion object {
        fun newInstance(): Fragment = TimelineFragment()
        fun newInstance(bundle: Bundle): Fragment = TimelineFragment().apply { arguments = bundle }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentTimelineBinding>(inflater, R.layout.fragment_timeline, container, false)
        adapter = TimelineRecyclerViewAdapter(context, tweetList)
        binding.viewModel = when {
            arguments == null -> TimelineViewModel(this)
            arguments[PagerAdapter.PAGE_TAG] == PagerAdapter.MENTION -> MentionViewModel(this)
            else -> TimelineViewModel(this)
        }
        binding.recyclerViewTimeline.adapter = adapter
        binding.recyclerViewTimeline.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTimeline.addItemDecoration(DividerItemDecoration(binding.recyclerViewTimeline.context, LinearLayoutManager(activity).orientation))
        return binding.root
    }

    //  ----implements TimelineFragmentContract----
    override fun startProfileActivity() {
        ProfileActivity.start(context)
    }

    override fun addTweet(tweet: Tweet) {
        tweetList.add(tweet)
        DataBindingUtil.getBinding<FragmentTimelineBinding>(view).recyclerViewTimeline.adapter.notifyItemInserted(tweetList.size)
    }

    override fun addTweetList(tweet: List<Tweet>) {
        tweetList.addAll(tweet)
        activity.runOnUiThread { adapter?.notifyDataSetChanged() }
    }

    override fun showSnackBar(msg: String) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
    }
}