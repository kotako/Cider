package info.kotlin.kotako.cider.view.listener

import android.content.Context
import android.support.design.widget.NavigationView
import android.view.MenuItem
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.view.activity.SettingsActivity

class NavigationDrawerListener(val context: Context) : NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {
                SettingsActivity.start(context)
            }
        }
        return false
    }
}