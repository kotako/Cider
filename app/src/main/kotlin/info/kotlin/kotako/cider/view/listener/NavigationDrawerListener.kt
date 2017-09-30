package info.kotlin.kotako.cider.view.listener

import android.app.Activity
import android.content.Context
import android.support.design.widget.NavigationView
import android.view.MenuItem
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.view.activity.AccountListActivity
import info.kotlin.kotako.cider.view.activity.SettingsActivity

class NavigationDrawerListener(val activity: Activity) : NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {
                SettingsActivity.start(activity)
            }
            R.id.nav_account -> {
                AccountListActivity.start(activity)
            }
        }
        return false
    }
}