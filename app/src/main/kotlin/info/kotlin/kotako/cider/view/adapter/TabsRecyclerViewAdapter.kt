package info.kotlin.kotako.cider.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.view.viewHolder.TabViewHolder

class TabsRecyclerViewAdapter(val context: Context, private val tabList: ArrayList<Tab>) : RecyclerView.Adapter<TabViewHolder>() {

    override fun getItemCount(): Int = tabList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder =
            TabViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_tab, parent, false))

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.iconView.setImageResource(tabList[position].icon)
        holder.nameView.text = "ほうむ"
    }
}