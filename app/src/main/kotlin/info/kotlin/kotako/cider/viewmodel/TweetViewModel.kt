package info.kotlin.kotako.cider.viewmodel

import android.content.Context
import android.util.Log
import com.twitter.sdk.android.core.models.User
import info.kotlin.kotako.cider.model.DateManager
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import java.util.*

class TweetViewModel(val context: Context) {

    fun onIconClicked(user: User) {
        Log.d("hoge", user.screenName)
        ProfileActivity.start(context, info.kotlin.kotako.cider.model.entity.User(user))
    }

    fun createdAtJpn(createdAt: String): String = DateManager.createdAt(createdAt, Locale.JAPAN)

    fun createdInterval(createdAt: String): String = DateManager.intervalFromCreated(createdAt)
}