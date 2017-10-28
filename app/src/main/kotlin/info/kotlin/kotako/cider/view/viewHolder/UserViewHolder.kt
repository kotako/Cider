package info.kotlin.kotako.cider.view.viewHolder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import info.kotlin.kotako.cider.databinding.ViewUserCellBinding

class UserViewHolder(view: View):  RecyclerView.ViewHolder(view){
    val binding = DataBindingUtil.bind<ViewUserCellBinding>(view)
}