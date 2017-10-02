package info.kotlin.kotako.cider.viewmodel

import android.view.MenuItem
import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract

class MainViewModel(private val mainView: MainActivityContract) {

    init {
        if (TwitterCore.getInstance().sessionManager?.activeSession == null) mainView.startAccountListActivity()
    }

    fun onFabClicked(view: View) {
        mainView.startPostActivity()
    }

    fun navigationOnClick(item: MenuItem) {
        mainView.closeDrawer()
        when (item.itemId) {
            R.id.nav_settings -> mainView.startSettingsActivity()
            R.id.nav_account -> mainView.startAccountListActivity()
        }
    }
}