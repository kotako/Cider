package info.kotlin.kotako.cider.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import info.kotlin.kotako.cider.BR
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.view.viewHolder.DMViewHolder

class DMRecyclerViewAdapter(val context: Context, val dmList: ArrayList<DirectMessage>) : RecyclerView.Adapter<DMViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DMViewHolder =
            DMViewHolder(LayoutInflater.from(context).inflate(R.layout.view_dm_cell, parent))

    override fun onBindViewHolder(holder: DMViewHolder?, position: Int) {
        holder?.binding?.setVariable(BR.dm, dmList[position])
    }

    override fun getItemCount(): Int = dmList.size
}