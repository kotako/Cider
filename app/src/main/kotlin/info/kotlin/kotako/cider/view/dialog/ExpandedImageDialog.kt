package info.kotlin.kotako.cider.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import info.kotlin.kotako.cider.R

class ExpandedImageDialog : DialogFragment() {

    companion object {
        fun newInstance(bundle: Bundle): DialogFragment = ExpandedImageDialog().apply { arguments = bundle }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_expanded_image, null)
        (view.findViewById(R.id.imageview_expanded_media) as ImageView).setOnLongClickListener { Toast.makeText(activity, "保存したよ", Toast.LENGTH_SHORT).show();false; }
        arguments?.let {
            Glide.with(activity)
                    .load(it.get("url"))
                    .apply(RequestOptions().fitCenter())
                    .into(view.findViewById(R.id.imageview_expanded_media) as ImageView)
        }
        return AlertDialog.Builder(activity).run {
            setView(view)
            create()
        }
    }
}