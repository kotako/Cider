package info.kotlin.kotako.cider.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import info.kotlin.kotako.cider.BR
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.view.viewHolder.DMViewHolder

class DMRecyclerViewAdapter(val context: Context, private val dmList: Map<Long, List<DirectMessage>>) : RecyclerView.Adapter<DMViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DMViewHolder =
            DMViewHolder(LayoutInflater.from(context).inflate(R.layout.view_dm_cell, parent, false))

    override fun onBindViewHolder(holder: DMViewHolder?, position: Int) {
        val key = dmList.map { it.key }[position]
        holder?.binding?.setVariable(BR.user, dmList[key]?.first()?.sender)
        holder?.binding?.setVariable(BR.dm, dmList[key]?.first())
    }

    override fun getItemCount(): Int = dmList.keys.size
}