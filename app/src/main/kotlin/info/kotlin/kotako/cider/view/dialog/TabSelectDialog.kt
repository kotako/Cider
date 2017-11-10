package info.kotlin.kotako.cider.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import info.kotlin.kotako.cider.model.TabManager

class TabSelectDialog : DialogFragment() {

    companion object {
        fun newInstance(): DialogFragment = TabSelectDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tabsList = arrayOf(
                TabManager.timelineTabDefault,
                TabManager.mentionTabDefault,
                TabManager.listTabDefault(0, "UserList"),
                TabManager.dmTabDefault)

        return AlertDialog.Builder(activity)
                .setItems(tabsList.map { it.name }.toTypedArray(), { dialog, which ->  Log.d("dev", tabsList[which].name); dismiss() })
                .create()
    }
}