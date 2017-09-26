package info.kotlin.kotako.cider.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.internal.TwitterApi
import com.twitter.sdk.android.core.internal.network.OkHttpClientHelper
import com.twitter.sdk.android.core.models.*
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

class APIClient(session: TwitterSession) {

//  Retrofit2の返す値は全てCall<T>でラップされてしまう
//  TwitterApiClientのObservableでラップさせる版です

    private val retrofit = Retrofit.Builder()
            .client(OkHttpClientHelper.getOkHttpClient(session, TwitterCore.getInstance().authConfig))
            .baseUrl(TwitterApi().baseHostUrl)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

    private fun buildGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(SafeListAdapter())
                .registerTypeAdapterFactory(SafeMapAdapter())
                .registerTypeAdapter(BindingValues::class.java, BindingValuesAdapter())
                .create()
    }

    @SuppressWarnings("unchecked")
    private fun <T : Any?> getService(cls: Class<T>?): T = retrofit.create(cls)

//  独自に定義するAPI
    fun TimelineObservable(): TimelineObservable = getService(TimelineObservable::class.java)

    interface TimelineObservable {
        @GET("/1.1/statuses/home_timeline.json")
        fun homeTimeline(@Query("count") count: Int?,
                         @Query("since_id") since_id: Long?,
                         @Query("max_id") max_id: Long?): Observable<List<Tweet>>
    }
}