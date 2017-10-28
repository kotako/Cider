package info.kotlin.kotako.cider.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import info.kotlin.kotako.cider.BR
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.view.viewHolder.TweetViewHolder
import info.kotlin.kotako.cider.viewmodel.TweetViewModel

class TimelineRecyclerViewAdapter(val context :Context,val tweetList: ArrayList<Tweet>) : RecyclerView.Adapter<TweetViewHolder>() {

    //  viewの作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder =
            TweetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_tweet_cell, parent, false))

    //  データのセット
    override fun onBindViewHolder(holder: TweetViewHolder?, position: Int) {
        holder?.binding?.setVariable(BR.tweet, tweetList[position])
        holder?.binding?.setVariable(BR.viewModel, TweetViewModel())
    }

    override fun getItemCount(): Int = tweetList.size
}