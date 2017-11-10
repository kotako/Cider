package info.kotlin.kotako.cider.view.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog

class LinkNavigationDialog : DialogFragment() {

    companion object {
        val ARGUMENT_KEY = "Link"
        fun newInstance(bundle: Bundle): DialogFragment = LinkNavigationDialog().apply { arguments = bundle }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val list = ArrayList<String>()
        arguments?.let { list.addAll(arguments.getStringArray(ARGUMENT_KEY)) }

        return AlertDialog.Builder(activity)
                .setTitle("Links")
                .setItems(list.toTypedArray(), { _: DialogInterface, which: Int -> openLinkWithBrowser(list[which]) })
                .create()
    }

    private fun openLinkWithBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}