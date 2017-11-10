package info.kotlin.kotako.cider.viewmodel

import info.kotlin.kotako.cider.contract.TabSettingsActivityContract

class TabSettingsViewModel(private val settingsView:TabSettingsActivityContract){

    fun onAddClicked(){
        settingsView.showTabSelectDialog()
    }
}