package info.kotlin.kotako.cider.model

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.support.v4.widget.SwipeRefreshLayout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Account
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

// TODO: できるだけ減らしていきたい

@BindingAdapter("loadCircleImage")
fun ImageView.loadCircleImage(url: String?) {
    url?.let {
        Glide.with(context)
                .load(it)
                .apply(RequestOptions().circleCrop())
                .into(this)
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context)
                .load(it)
                .into(this)
    }
}

@BindingAdapter("loadImageFromSession")
fun ImageButton.loadImageFromSession(account: Account) {
    var profileImageUrl: String? = null
    val session = TwitterSession(TwitterAuthToken(account.token, account.tokenSecret), account.userId, account.userName)

    RestAPIClient(session).UsersObservable()
            .showUser(session.userId, null, null)
            .subscribeOn(Schedulers.newThread())
            .subscribe(DefaultObserver(
                    next = { profileImageUrl = it.profileImageUrl },
                    completed = { this.post { loadCircleImage(profileImageUrl) } }))
}

@BindingAdapter("onImageClick")
fun ImageButton.onImageClicked(userId:Long){
    this.setOnClickListener { ProfileActivity.start(this.context, userId) }
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.onRefresh(listener: SwipeRefreshLayout.OnRefreshListener) {
    this.setColorSchemeResources(R.color.colorAccent)
    this.setOnRefreshListener(listener)
}

@BindingAdapter("markupUser")
fun TextView.markupUser(tweet: Tweet) {
    this.text = SpannableStringBuilder().apply {
        append(tweet.text)
        val screenNameMatcher = Pattern.compile("\\@(\\w+)").matcher(tweet.text)
        val urlMatcher = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+").matcher(tweet.text)
        while (screenNameMatcher.find()) setSpan(UnderlineSpan(), screenNameMatcher.start(), screenNameMatcher.end(), Spanned.SPAN_COMPOSING)
        while (urlMatcher.find()) setSpan(UnderlineSpan(), urlMatcher.start(), urlMatcher.end(), Spanned.SPAN_COMPOSING)
    }
}