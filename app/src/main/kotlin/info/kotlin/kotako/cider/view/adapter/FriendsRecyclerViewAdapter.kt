package info.kotlin.kotako.cider.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import info.kotlin.kotako.cider.BR
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.view.viewHolder.UserViewHolder

class FriendsRecyclerViewAdapter(val context: Context, private val friendsList: ArrayList<User>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder =
            UserViewHolder(LayoutInflater.from(context).inflate(R.layout.view_user_cell, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        holder?.binding?.setVariable(BR.user, friendsList[position])
    }

    override fun getItemCount(): Int = friendsList.size
}