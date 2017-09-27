package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.Tweet

interface TimelineFragmentContract {
    fun startProfileActivity()
    fun addTweet(tweet: Tweet)
    fun addTweetList(tweet: List<Tweet>)
    fun showSnackBar(msg:String)
}