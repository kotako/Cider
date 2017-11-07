package info.kotlin.kotako.cider.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.FragmentDirectMessagesBinding
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.view.adapter.DMRecyclerViewAdapter

class DirectMessagesFragment : Fragment() {

    private var dmList = ArrayList<DirectMessage>()
    private lateinit var binding: FragmentDirectMessagesBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_direct_messages, container, false)

        binding.recyclerViewDm.apply {
            adapter = DMRecyclerViewAdapter(activity, dmList)
            invalidateItemDecorations()
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }
}