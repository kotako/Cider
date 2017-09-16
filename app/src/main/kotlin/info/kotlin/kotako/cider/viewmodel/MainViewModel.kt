package info.kotlin.kotako.cider.viewmodel

import android.view.View
import info.kotlin.kotako.cider.contract.MainActivityContract

class MainViewModel(private val mainView: MainActivityContract) {

    fun onFabClicked(view: View) {
        mainView.startPostActivity()
    }
}