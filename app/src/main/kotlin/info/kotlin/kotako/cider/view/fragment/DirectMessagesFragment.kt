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
import info.kotlin.kotako.cider.contract.DMFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentDirectMessagesBinding
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.view.adapter.DMRecyclerViewAdapter
import info.kotlin.kotako.cider.viewmodel.DMFragmentViewModel

class DirectMessagesFragment : Fragment(), DMFragmentContract {

    private var dmMap = mutableMapOf<User, List<DirectMessage>>()
    private lateinit var binding: FragmentDirectMessagesBinding

    companion object {
        fun newInstance(): Fragment = DirectMessagesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_direct_messages, container, false)

        binding.apply {
            viewModel = DMFragmentViewModel(this@DirectMessagesFragment)
            recyclerViewDm.apply {
                adapter = DMRecyclerViewAdapter(activity, dmMap)
                layoutManager = LinearLayoutManager(context)
                invalidateItemDecorations()
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            }
            layoutDmRefresh.setOnRefreshListener { binding.viewModel?.refresh() }
        }
        return binding.root
    }

    override fun onStart() {
        binding.viewModel?.start()
        super.onStart()
    }

    override fun onDestroy() {
        binding.viewModel?.stop()
        super.onDestroy()
    }

//  override DMFragmentContract

    override fun addDMCollection(newDmMap: Map<User, List<DirectMessage>>) {
        dmMap.putAll(newDmMap)
        activity.runOnUiThread {
            binding.recyclerViewDm.adapter.notifyDataSetChanged()
        }
    }

    override fun clearDMList() {
        dmMap.clear()
        activity.runOnUiThread {
            binding.recyclerViewDm.adapter.notifyDataSetChanged()
        }
    }

    override fun dMMapSize(): Int = dmMap.keys.size

    override fun showProgressBar() {
        activity.runOnUiThread { binding.layoutDmRefresh.isRefreshing = true }
    }

    override fun hideProgressBar() {
        activity.runOnUiThread { binding.layoutDmRefresh.isRefreshing = false }
    }

    override fun showSnackBar(msg: String) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT) }
    }
}