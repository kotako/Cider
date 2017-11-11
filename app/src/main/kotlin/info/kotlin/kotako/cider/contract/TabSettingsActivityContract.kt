package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.Tab

interface TabSettingsActivityContract {
    fun showTabSelectDialog()
    fun addTab(tab: Tab)
    fun removeTab(position: Int)
}
