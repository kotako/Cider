package info.kotlin.kotako.cider.model.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.twitter.sdk.android.core.models.*
import com.twitter.sdk.android.core.models.HashtagEntity
import com.twitter.sdk.android.core.models.MediaEntity
import com.twitter.sdk.android.core.models.Tweet
import info.kotlin.kotako.cider.BR
import twitter4j.*
import java.io.Serializable

//TODO: 動画付きのExtendedMediaへの対応

class Tweet(val id: Long, val createdAt: String, favoriteCount: Int, favorited: Boolean, retweeted: Boolean,
            retweetCount: Int, val user: User, val text: String, val source: String, val inReplyToStatusId: Long?,
            val inReplyToUserId: Long?, val inReplyToScreenName: String?, val hashtagEntity: List<HashtagEntity>?,
            val urlEntity: List<UrlEntity>?, val userMentionEntity: List<MentionEntity>?,
            val mediaEntity: List<MediaEntity>?, val extendedMediaEntity: List<ExtendedMediaEntity>?, val retweetedUser: User?) : Serializable, BaseObservable() {

    constructor(tweet:Tweet, retweetedUser:User? = null):this(
            tweet.id, tweet.createdAt, tweet.favoriteCount, tweet.favorited, tweet.retweeted,
            tweet.retweetCount, User(tweet.user), tweet.text, tweet.source, tweet.inReplyToStatusId,
            tweet.inReplyToUserId, tweet.inReplyToScreenName, tweet.extendedEntities.hashtags , tweet.extendedEntities.urls,
            tweet.extendedEntities.userMentions, tweet.extendedEntities.media, null, retweetedUser
    )

    constructor(status:Status, retweetedUser: User? = null):this(
            status.id, status.createdAt.toString(), status.favoriteCount, status.isFavorited, status.isRetweeted,
            status.retweetCount, User(status.user), status.text, status.source, status.inReplyToStatusId,
            status.inReplyToUserId,status.inReplyToScreenName, TweetExtended.toHashTagEntityList(status.hashtagEntities),
            TweetExtended.toUrlEntityList(status.urlEntities),TweetExtended.toMentionEntityList(status.userMentionEntities),
            TweetExtended.toMediaEntityList(status.extendedMediaEntities), status.extendedMediaEntities.toList(), retweetedUser
    )

    @get:Bindable
    var favoriteCount = favoriteCount
        set(value) {
            field = value
            notifyPropertyChanged(BR.favoriteCount)
        }
    @get:Bindable
    var favorited = favorited
        set(value) {
            field = value
            notifyPropertyChanged(BR.favorited)
        }
    @get:Bindable
    var retweeted = retweeted
        set(value) {
            field = value
            notifyPropertyChanged(BR.retweeted)
        }
    @get:Bindable
    var retweetCount = retweetCount
        set(value) {
            field = value
            notifyPropertyChanged(BR.retweetCount)
        }
    @get:Bindable
    var expanded = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.expanded)
        }
}