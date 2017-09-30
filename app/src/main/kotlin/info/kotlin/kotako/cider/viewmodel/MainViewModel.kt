package info.kotlin.kotako.cider.viewmodel

import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.MainActivityContract

class MainViewModel(private val mainView: MainActivityContract) {

    init {
        if (TwitterCore.getInstance().sessionManager?.activeSession == null) {
            mainView.startAccountListActivity()
        }
    }

    fun onFabClicked(view: View) {
        mainView.startPostActivity()
    }
}