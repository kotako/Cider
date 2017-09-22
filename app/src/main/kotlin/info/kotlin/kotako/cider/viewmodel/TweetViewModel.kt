package info.kotlin.kotako.cider.viewmodel

import android.content.Context
import android.view.View
import info.kotlin.kotako.cider.view.activity.ProfileActivity

class TweetViewModel(val context:Context) {

    fun onIconClicked(view:View) {
        ProfileActivity.start(context)
    }

}