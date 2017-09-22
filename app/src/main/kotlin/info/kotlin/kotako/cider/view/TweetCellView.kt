package info.kotlin.kotako.cider.view

import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.RelativeLayout
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.ViewTweetCellBinding
import info.kotlin.kotako.cider.viewmodel.TimelineViewModel

class TweetCellView(context:Context): RelativeLayout(context) {

    init {
        val binding = DataBindingUtil.inflate<ViewTweetCellBinding>(LayoutInflater.from(context), R.layout.view_tweet_cell, this, true)
    }

}
