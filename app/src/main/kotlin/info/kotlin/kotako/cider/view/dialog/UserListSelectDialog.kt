package info.kotlin.kotako.cider.view.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import info.kotlin.kotako.cider.contract.TabSettingsActivityContract
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.UserList

class UserListSelectDialog : DialogFragment() {

    private var list = ArrayList<UserList>()

    companion object {
        fun newInstance(): DialogFragment = UserListSelectDialog()
        fun newInstance(list: Array<UserList>): DialogFragment = UserListSelectDialog().apply { arguments = Bundle().apply { putSerializable("key", list) } }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        list.addAll(arguments.getSerializable("key") as Array<out UserList>)
        return AlertDialog.Builder(activity)
                .setItems(list.map { it.name }.toTypedArray(),
                        { dialog, which ->
                            setTab(list[which].id, list[which].name)
                            dialog.dismiss()
                        })
                .create()
    }

    private fun setTab(listId: Long, listName: String) {
        (activity as TabSettingsActivityContract).addTab(TabManager.listTabDefault(listId, listName))
    }
}