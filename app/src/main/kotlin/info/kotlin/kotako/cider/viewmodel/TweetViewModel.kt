package info.kotlin.kotako.cider.viewmodel

import android.content.Context
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.User
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.model.DateManager
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import rx.schedulers.Schedulers
import java.util.*

class TweetViewModel(val context: Context) {

    fun onIconClicked(user: User) {
        //Log.d("hoge", user.screenName)
        ProfileActivity.start(context, user.id)
    }

    fun createdAtJpn(createdAt: String): String = DateManager.createdAt(createdAt, Locale.JAPAN)

    fun createdInterval(createdAt: String): String = DateManager.intervalFromCreated(createdAt)

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
}