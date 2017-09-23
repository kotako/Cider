package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.Tweet

interface TimelineFragmentContract {
    fun startProfileActivity()
    fun addTweet(tweet:Tweet)
    fun showSnackBar(msg:String)
}