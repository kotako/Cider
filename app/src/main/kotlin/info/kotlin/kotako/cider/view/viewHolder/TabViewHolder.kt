package info.kotlin.kotako.cider.view.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import info.kotlin.kotako.cider.R

class TabViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val iconView = view.findViewById(R.id.imageview_tab_icon) as ImageView
    val nameView = view.findViewById(R.id.textview_tab_name) as TextView
}