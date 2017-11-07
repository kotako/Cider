package info.kotlin.kotako.cider.view.viewHolder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import info.kotlin.kotako.cider.databinding.ViewDmCellBinding

class DMViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = DataBindingUtil.bind<ViewDmCellBinding>(itemView)
}