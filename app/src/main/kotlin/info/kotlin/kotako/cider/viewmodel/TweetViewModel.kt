package info.kotlin.kotako.cider.viewmodel

import android.content.Context
import android.view.View
import info.kotlin.kotako.cider.model.DateManager
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import java.util.*

class TweetViewModel(val context: Context) {

    fun onIconClicked(view: View) {
        ProfileActivity.start(context)
    }

    fun createdAtJpn(createdAt: String): String = DateManager.createdAt(createdAt, Locale.JAPAN)

    fun createdInterval(createdAt: String): String = DateManager.intervalFromCreated(createdAt)
}