package info.kotlin.kotako.cider.viewmodel

import android.view.View
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.model.Account
import io.realm.Realm

class MainViewModel(private val mainView: MainActivityContract) {

    fun onFabClicked(view: View) {
        mainView.startAccountListActivity()
    }
}