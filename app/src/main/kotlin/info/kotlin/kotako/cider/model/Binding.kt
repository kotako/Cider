package info.kotlin.kotako.cider.model

import android.databinding.BindingAdapter
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("loadImage")
fun ImageButton.loadImage(url: String) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .into(this)
}