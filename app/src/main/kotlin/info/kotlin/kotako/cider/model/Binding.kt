package info.kotlin.kotako.cider.model

import android.databinding.BindingAdapter
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("loadImage")
fun ImageButton.loadImage(url: String?) {
    url?.let {
        Glide.with(context)
                .load(it)
                .apply(RequestOptions().circleCrop())
                .into(this)
    }
}