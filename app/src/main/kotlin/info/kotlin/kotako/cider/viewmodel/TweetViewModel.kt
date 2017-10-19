package info.kotlin.kotako.cider.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.DateConverter
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import info.kotlin.kotako.cider.view.dialog.ExpandedImageDialog
import info.kotlin.kotako.cider.view.activity.PostActivity
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import rx.schedulers.Schedulers
import java.util.*

class TweetViewModel(val context: Context) {

    fun onIconClicked(tweet: Tweet) = ProfileActivity.start(context, tweet.user.id)

    fun createdAtJpn(createdAt: String): String = DateConverter.createdAt(createdAt, Locale.JAPAN)

    fun createdInterval(createdAt: String): String = DateConverter.intervalFromCreated(createdAt)

    fun onImageClicked(url:String) {
        ExpandedImageDialog
                .newInstance(Bundle().apply { putString("url", url) })
                .show((context as Activity).fragmentManager, "expandedImage")
    }

    fun onTweetClicked(tweet: Tweet) {
        tweet.expanded = tweet.expanded.not()
    }

    fun onReplyClicked(tweet: Tweet) = PostActivity.start(context, tweet)

    fun onFavoriteClicked(tweet: Tweet) {
        if (tweet.favorited) {
            RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .FavoriteObservable()
                    .unFavorite(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.favorited = false
                                tweet.favoriteCount--
                            }))
        } else {
            RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .FavoriteObservable()
                    .favorite(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.favorited = true
                                tweet.favoriteCount++
                            }))
        }
    }

    fun onRetweetClicked(tweet: Tweet) {
        if (tweet.retweeted) {
            RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .RetweetObservable()
                    .unRetweet(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.retweeted = false
                                tweet.retweetCount--
                            }))
        } else {
            RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .RetweetObservable()
                    .retweet(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.retweeted = true
                                tweet.retweetCount++
                            }))
        }
    }
}