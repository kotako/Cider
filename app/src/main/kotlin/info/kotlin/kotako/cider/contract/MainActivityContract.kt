package info.kotlin.kotako.cider.contract

import android.content.Context

interface MainActivityContract {
    fun startPostActivity()
    fun startAccountListActivity()
    fun startSettingsActivity()
    fun startTabsSettingsActivity()
    fun getContext(): Context
    fun showSnackBar(msg: String)
    fun openDrawer(): Unit?
    fun closeDrawer(): Unit?
    fun checkNavigationItem(id: Int)
    fun setTitle(title: String)
}
