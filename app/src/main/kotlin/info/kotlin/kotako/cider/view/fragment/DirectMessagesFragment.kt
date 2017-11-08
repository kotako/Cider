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
import info.kotlin.kotako.cider.contract.DMFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentDirectMessagesBinding
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.view.adapter.DMRecyclerViewAdapter
import info.kotlin.kotako.cider.viewmodel.DMFragmentViewModel

class DirectMessagesFragment : Fragment(), DMFragmentContract {

    private var dmMap = linkedMapOf<Long, ArrayList<DirectMessage>>()
    private lateinit var binding: FragmentDirectMessagesBinding

    companion object {
        val SAVED_DM_MAP_KEY = "savedDmMapKey"
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
        super.onStart()
        if (TwitterCore.getInstance().sessionManager?.activeSession == null) return
        binding.viewModel?.start()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(SAVED_DM_MAP_KEY, dmMap)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { dmMap.putAll(it.getSerializable(SAVED_DM_MAP_KEY) as Map<Long, ArrayList<DirectMessage>>) }
        binding.recyclerViewDm.adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewModel?.stop()
    }

//  override DMFragmentContract

    override fun addDm(dm: DirectMessage) {
        dmMap.put(dm.sender.id, dmMap[dm.sender.id]?.apply { add(0, dm) } ?: arrayListOf(dm))
        Log.d("dev", dmMap.keys.indexOf(dm.sender.id).toString())
        activity.runOnUiThread { binding.recyclerViewDm.adapter.notifyItemChanged(dmMap.keys.indexOf(dm.sender.id)) }
    }

    override fun addDMCollection(newDmMap: Map<Long, ArrayList<DirectMessage>>) {
        dmMap.putAll(newDmMap)
        activity.runOnUiThread { binding.recyclerViewDm.adapter.notifyDataSetChanged() }
    }

    override fun clearDMList() {
        dmMap.clear()
        activity.runOnUiThread {
            binding.recyclerViewDm.adapter.notifyDataSetChanged()
        }
    }

    override fun dMMapSize(): Int = dmMap.keys.size

    override fun isEmpty(): Boolean = dmMap.isEmpty()

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