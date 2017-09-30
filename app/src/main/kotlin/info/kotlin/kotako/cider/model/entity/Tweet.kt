package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import java.io.Serializable

class Tweet(tweet: Tweet, val retweetedUser: User? = null) : Serializable {

    val id = tweet.id
    val createdAt = tweet.createdAt
    val favoriteCount = tweet.favoriteCount
    val favorited = tweet.favorited
    val retweeted = tweet.retweeted
    val retweetCount = tweet.retweetCount
    val user = tweet.user
    val user_sn = tweet.user.screenName
    val text = tweet.text
    val source = tweet.source
    val inReplyToStatusId = tweet.inReplyToStatusId
    val inReplyToUserId = tweet.inReplyToUserId
    val inReplyToScreenName = tweet.inReplyToScreenName
}