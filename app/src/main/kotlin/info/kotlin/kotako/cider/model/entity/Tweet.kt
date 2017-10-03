package info.kotlin.kotako.cider.model.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.twitter.sdk.android.core.models.Tweet
import info.kotlin.kotako.cider.BR
import java.io.Serializable

class Tweet(tweet: Tweet, val retweetedUser: User? = null) : Serializable, BaseObservable() {

    val id = tweet.id
    val createdAt = tweet.createdAt
    @get:Bindable
    var favoriteCount = tweet.favoriteCount
        set(value) {
            field = value
            notifyPropertyChanged(BR.favoriteCount)
        }
    @get:Bindable
    var favorited = tweet.favorited
        set(value) {
            field = value
            notifyPropertyChanged(BR.favorited)
        }
    @get:Bindable
    var retweeted = tweet.retweeted
        set(value) {
            field = value
            notifyPropertyChanged(BR.retweeted)
        }
    @get:Bindable
    var retweetCount = tweet.retweetCount
        set(value) {
            field = value
            notifyPropertyChanged(BR.retweetCount)
        }
    val user = User(tweet.user)
    val user_sn = tweet.user.screenName
    val text = tweet.text
    val source = tweet.source
    val inReplyToStatusId = tweet.inReplyToStatusId
    val inReplyToUserId = tweet.inReplyToUserId
    val inReplyToScreenName = tweet.inReplyToScreenName

    @get:Bindable
    var expanded = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.expanded)
        }
}