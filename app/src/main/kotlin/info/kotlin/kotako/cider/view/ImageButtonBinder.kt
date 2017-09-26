package info.kotlin.kotako.cider.view

import android.databinding.BindingAdapter
import android.widget.ImageButton
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun ImageButton.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}