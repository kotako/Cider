package info.kotlin.kotako.cider.viewmodel

import android.view.View
import info.kotlin.kotako.cider.contract.TimelineFragmentContract

class TimelineViewModel(val timelineView: TimelineFragmentContract) {

    fun onIconClicked(view: View){
//      ウィコンをクリックするとそのユーザのプロフィールを表示
        timelineView.startProfileActivity()
    }
}