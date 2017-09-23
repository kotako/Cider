package info.kotlin.kotako.cider.model

import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call
import retrofit2.Response
import rx.Observable
import rx.Observer

class TimelineManager {

    companion object {
        val twitterInstance = TwitterCore.getInstance()
        fun getInstance(): TimelineManager = TimelineManager()
    }

    fun getActiveUserId(): Long? = twitterInstance.sessionManager?.activeSession?.userId

    fun loadTweet(observer: Observer<info.kotlin.kotako.cider.model.Tweet>, sinceId: Long? = null) {
        if (getActiveUserId() == null) return
        twitterInstance.apiClient.statusesService.homeTimeline(50, sinceId, null, false, false, false, false)
                .enqueue(object : retrofit2.Callback<List<Tweet>> {
                    override fun onResponse(call: Call<List<Tweet>>?, response: Response<List<Tweet>>) {
                        Observable.from(response.body())
                                .map { t -> info.kotlin.kotako.cider.model.Tweet(t) }
                                .subscribe(observer)
                    }

                    override fun onFailure(call: Call<List<Tweet>>?, t: Throwable?) {
                        val observables = Observable.error<Throwable>(t)
                    }
                })
    }

}