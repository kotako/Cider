package info.kotlin.kotako.cider.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentTimelineBinding
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import info.kotlin.kotako.cider.view.TimelineRecyclerViewAdapter
import info.kotlin.kotako.cider.viewmodel.TimelineViewModel

class TimelineFragment: Fragment(), TimelineFragmentContract{

    companion object {
        fun newInstance(): Fragment = TimelineFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentTimelineBinding>(inflater, R.layout.fragment_timeline, container, false)
        binding.viewModel = TimelineViewModel(this)
        binding.recyclerViewTimeline.adapter = TimelineRecyclerViewAdapter()
        binding.recyclerViewTimeline.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    //  ----implements TimelineFragmentContract----
    override fun startProfileActivity() {
        ProfileActivity.start(context)
    }
}