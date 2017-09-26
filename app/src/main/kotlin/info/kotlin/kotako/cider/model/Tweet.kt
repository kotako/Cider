package info.kotlin.kotako.cider.model

import com.twitter.sdk.android.core.models.Tweet

class Tweet(tweet: Tweet) {

    val id = tweet.id
    val createdAt = tweet.createdAt
    val favoriteCount = tweet.favoriteCount
    val favorited = tweet.favorited
    val retweetCount = tweet.retweetCount
    val retweeted = tweet.retweeted
    var retweetedStatus: info.kotlin.kotako.cider.model.Tweet? = if (retweeted) info.kotlin.kotako.cider.model.Tweet(tweet.retweetedStatus) else null
    val user = tweet.user
    val user_sn = "@" + tweet.user.screenName
    val text = tweet.text
}