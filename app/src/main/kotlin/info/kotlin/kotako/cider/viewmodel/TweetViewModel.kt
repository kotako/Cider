package info.kotlin.kotako.cider.viewmodel

import android.content.Context
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.DateManager
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import info.kotlin.kotako.cider.view.activity.PostActivity
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import rx.schedulers.Schedulers
import java.util.*

class TweetViewModel(val context: Context) {

    fun onIconClicked(tweet: Tweet) {
        //Log.d("hoge", user.screenName)
        ProfileActivity.start(context, tweet.user.id)
    }

    fun createdAtJpn(createdAt: String): String = DateManager.createdAt(createdAt, Locale.JAPAN)

    fun createdInterval(createdAt: String): String = DateManager.intervalFromCreated(createdAt)

    fun onTweetClicked(tweet:Tweet) {
        tweet.expanded = tweet.expanded.not()
    }

    fun onReplyClicked(tweet: Tweet) {
        PostActivity.start(context, tweet)
    }

    fun onFavoriteClicked(tweet: Tweet) {
        if (tweet.favorited) {
            APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .FavoriteObservable()
                    .unFavorite(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.favorited = false
                                tweet.favoriteCount--
                            }))
        } else {
            APIClient(TwitterCore.getInstance().sessionManager.activeSession)
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
            APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                    .RetweetObservable()
                    .unRetweet(tweet.id)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(DefaultObserver(
                            completed = {
                                tweet.retweeted = false
                                tweet.retweetCount--
                            }))
        } else {
            APIClient(TwitterCore.getInstance().sessionManager.activeSession)
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