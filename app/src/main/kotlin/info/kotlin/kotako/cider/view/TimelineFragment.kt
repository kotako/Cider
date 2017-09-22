package info.kotlin.kotako.cider.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.TimelineFragmentContract
import info.kotlin.kotako.cider.databinding.ViewTweetCellBinding
import info.kotlin.kotako.cider.viewmodel.TimelineViewModel

class TimelineFragment: Fragment(), TimelineFragmentContract{

    companion object {
        fun newInstance(): Fragment = TimelineFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //val binding = DataBindingUtil.inflate<ViewTweetCellBinding>(inflater, R.layout.fragment_timeline, container, false)
        //binding.viewModel = TimelineViewModel(this)
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    //  ----implements TimelineFragmentContract----
    override fun startProfileActivity() {
        ProfileActivity.start(context)
    }
}