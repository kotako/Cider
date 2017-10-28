package info.kotlin.kotako.cider.view.dialog

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.ImageManager

class ExpandedImageDialog : DialogFragment() {

    companion object {
        fun newInstance(bundle: Bundle): DialogFragment = ExpandedImageDialog().apply { arguments = bundle }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_expanded_image, null)
        arguments?.let { arg ->
            Glide.with(activity)
                    .asBitmap()
                    .load(arg.get("url"))
                    .apply(RequestOptions().fitCenter())
                    .into(view.findViewById(R.id.imageview_expanded_media) as ImageView)

            (view.findViewById(R.id.imageview_expanded_media) as ImageView).setOnLongClickListener {
//              ストレージ書き込み権限の確認
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                    }
                } else {
//                  ストレージ書き込み可能な場合はオリジナルの画像を保存
                    Glide.with(activity)
                            .asBitmap()
                            .load(arg.get("url"))
                            .into(object : SimpleTarget<Bitmap>(arg.getInt("w"), arg.getInt("h")) {
                                override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                                    resource?.let {
                                        ImageManager.run {
                                            if (saveBitMapAsPng(activity, getCurrentStorageDirectory(activity), resource)) Toast.makeText(activity, "画像を保存したよ", Toast.LENGTH_SHORT).show()
                                            else Toast.makeText(activity, "画像を保存できませんでした", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            })
                }
                false
            }
        }
        return AlertDialog.Builder(activity).run {
            setView(view)
            create()
        }
    }
}