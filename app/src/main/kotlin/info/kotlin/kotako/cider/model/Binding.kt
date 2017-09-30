package info.kotlin.kotako.cider.model

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import info.kotlin.kotako.cider.model.entity.Account
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers

@BindingAdapter("loadImage")
fun ImageButton.loadImage(url: String?) {
    url?.let {
        Glide.with(context)
                .load(it)
                .apply(RequestOptions().circleCrop())
                .into(this)
    }
}

@BindingAdapter("loadImageFromSession")
fun ImageButton.loadImageFromSession(account: Account) {
    var profileImageUrl: String? = null
    val session = TwitterSession(TwitterAuthToken(account.token, account.tokenSecret), account.userId, account.userName)

    APIClient(session).UsersObservable()
            .showUser(session.userId, null, null)
            .subscribeOn(Schedulers.newThread())
            .subscribe(DefaultObserver(
                    next = { profileImageUrl = it.profileImageUrl },
                    completed = { this.post { loadImage(profileImageUrl) } }))
}

@BindingAdapter("tintSetRT")
fun ImageButton.setTintRT(mentioned: Boolean) {
    setColorFilter(Color.parseColor(if (mentioned) "#2ECC71" else "#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
}

@BindingAdapter("tintSetFav")
fun ImageButton.setTintFav(mentioned: Boolean) {
    setColorFilter(Color.parseColor(if (mentioned) "#E74C3C" else "#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
}
