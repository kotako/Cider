package info.kotlin.kotako.cider.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.internal.TwitterApi
import com.twitter.sdk.android.core.internal.network.OkHttpClientHelper
import com.twitter.sdk.android.core.models.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import info.kotlin.kotako.cider.model.entity.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class RestAPIClient(session: TwitterSession) {

//  Retrofit2の返す値は全てCall<T>でラップされてしまう
//  TwitterCore.TwitterApiClientのObservableでラップさせる版

    private val retrofit = Retrofit.Builder()
            .client(OkHttpClientHelper.getOkHttpClient(session, TwitterCore.getInstance().authConfig))
            .baseUrl(TwitterApi().baseHostUrl)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    fun DirectMessagesObservable(): DirectMessagesObservable = getService(DirectMessagesObservable::class.java)

    fun UsersObservable(): UsersObservable = getService(UsersObservable::class.java)

    fun PostObservable(): PostObservable = getService(PostObservable::class.java)

    fun FavoriteObservable(): FavoriteObservable = getService(FavoriteObservable::class.java)

    fun RetweetObservable(): RetweetObservable = getService(RetweetObservable::class.java)

    fun FriendShipObservable(): FriendShipObservable = getService(FriendShipObservable::class.java)

    interface TimelineObservable {
        @GET("/1.1/statuses/home_timeline.json")
        fun homeTimeline(@Query("count") count: Int?,
                         @Query("since_id") since_id: Long?,
                         @Query("max_id") max_id: Long?,
                         @Query("trim_user") trim_user: Boolean?,
                         @Query("exclude_replies") exclude_replies: Boolean?,
                         @Query("include_entities") include_entities: Boolean?): Observable<List<Tweet>>

        @GET("/1.1/statuses/user_timeline.json")
        fun userTimeline(@Query("user_id") user_id: Long?,
                         @Query("screen_name") screen_name: String?,
                         @Query("since_id") since_id: Long?,
                         @Query("count") count: Int?,
                         @Query("max_id") max_id: Long?,
                         @Query("trim_user") trim_user: Boolean?,
                         @Query("exclude_replies") exclude_replies: Boolean?,
                         @Query("include_rts") include_rts: Boolean?): Observable<List<Tweet>>

        @GET("/1.1/statuses/mentions_timeline.json")
        fun mentionTimeline(@Query("count") count: Int?,
                            @Query("since_id") since_id: Long?,
                            @Query("max_id") max_id: Long?,
                            @Query("trim_user") trim_user: Boolean?,
                            @Query("include_entities") include_entities: Boolean?): Observable<List<Tweet>>

        @GET("/1.1/collections/entries.json")
        fun collectionsTimeline(@Query("id") collectionId: String,
                                @Query("count") count: Int?,
                                @Query("max_position") max_position: Long?,
                                @Query("min_position") min_position: Long?): Observable<TweetCollection>

        @GET("/1.1/search/tweets.json")
        fun searchTimeline(@Query("q") query: String,
                           @Query("geocode") geocode: String?,
                           @Query("lang") lang: String?,
                           @Query("local") local: String?,
                           @Query("result_type") result_type: String?,
                           @Query("count") count: Int?,
                           @Query("until") until: String?,
                           @Query("since_id") since_id: Long?,
                           @Query("max_id") max_id: Long?,
                           @Query("include_entities") include_entities: Boolean?): Observable<SearchResponse>

        @GET("/1.1/lists/statuses.json")
        fun listTimeline(@Query("list_id") list_id: Long,
                         @Query("slug") slug: Long?,
                         @Query("owner_screen_name") owner_screen_name: String?,
                         @Query("owner_id") owner_id: Long?,
                         @Query("since_id") since_id: Long?,
                         @Query("max_id") max_id: Long?,
                         @Query("count") count: Int?,
                         @Query("include_entities") include_entities: Boolean?,
                         @Query("include_rts") include_rts: Boolean?): Observable<List<Tweet>>
    }

    interface DirectMessagesObservable {
        @GET("/1.1/direct_messages.json")
        fun directMessages(@Query("since_id") since_id: Long?,
                           @Query("max_id") max_id: Long?,
                           @Query("count") count: Int?,
                           @Query("include_entities") include_entities: Boolean?,
                           @Query("skip_status") skip_status: Boolean?): Observable<List<DirectMessage>>

        @GET("/1.1/direct_messages/sent.json")
        fun sentDirectMessages(@Query("since_id") since_id: Long?,
                               @Query("max_id") max_id: Long?,
                               @Query("count") count: Int?,
                               @Query("page") page: Int?,
                               @Query("include_entities") include_entities: Boolean?): Observable<List<DirectMessage>>

        @POST("/1.1/direct_messages/new.json")
        fun sendDirectMessage(@Query("user_id") user_id: Long?,
                              @Query("screen_name") screen_name: String?,
                              @Query("text") text: String): Observable<DirectMessage>
    }

    interface UsersObservable {
        @GET("/1.1/users/show.json")
        fun showUser(@Query("user_id") user_id: Long?,
                     @Query("screen_name") screen_name: String?,
                     @Query("include_entities") include_entities: Boolean?): Observable<User>

        @GET("/1.1/lists/list.json")
        fun showUserLists(@Query("user_id") user_id: Long?,
                          @Query("screen_name") screen_name: String?,
                          @Query("reverse") reverse: Boolean?): Observable<List<UserList>>

        @GET("/1.1/lists/show.json")
        fun showUserList(@Query("list_id") list_id: Long,
                         @Query("slug") slug: Long?,
                         @Query("owner_screen_name") owner_screen_name: String?,
                         @Query("owner_id") owner_id: Long?): Observable<UserList>

        @GET("/1.1/lists/members.json")
        fun showUserListMember(@Query("list_id") list_id: Long,
                               @Query("slug") slug: Long?,
                               @Query("owner_screen_name") owner_screen_name: String?,
                               @Query("owner_id") owner_id: Long?,
                               @Query("count") count: Int?,
                               @Query("cursor") cursor: Long?,
                               @Query("include_entities") include_entities: Boolean?,
                               @Query("skip_status") skip_status: Boolean?): Observable<ListMembers>
    }

    interface PostObservable {
        @POST("/1.1/statuses/update.json")
        fun post(@Query("status") status: String,
                 @Query("in_reply_to_status_id") in_reply_to_status_id: Long?,
                 @Query("possibly_sensitive") possibly_sensitive: Boolean?,
                 @Query("media_ids") media_ids: Long?): Observable<Tweet>
    }

    interface FavoriteObservable {
        @POST("/1.1/favorites/create.json")
        fun favorite(@Query("id") id: Long): Observable<Tweet>

        @POST("/1.1/favorites/destroy.json")
        fun unFavorite(@Query("id") id: Long): Observable<Tweet>
    }

    interface RetweetObservable {
        @POST("/1.1/statuses/retweet/{id}.json")
        fun retweet(@Path("id") id: Long): Observable<Tweet>

        @POST("/1.1/statuses/unretweet/{id}.json")
        fun unRetweet(@Path("id") id: Long): Observable<Tweet>
    }

    interface FriendShipObservable {
        @GET("/1.1/followers/list.json")
        fun followers(@Query("user_id") user_id: Long?,
                      @Query("screen_name") screen_name: String?,
                      @Query("cursor") cursor: Long?,
                      @Query("count") count: Int?,
                      @Query("skip_status") skip_status: Boolean?,
                      @Query("include_user_entities") include_user_entities: Boolean?): Observable<Users>

        @GET("/1.1/friends/list.json")
        fun friends(@Query("user_id") user_id: Long?,
                    @Query("screen_name") screen_name: String?,
                    @Query("cursor") cursor: Long?,
                    @Query("count") count: Int?,
                    @Query("skip_status") skip_status: Boolean?,
                    @Query("include_user_entities") include_user_entities: Boolean?): Observable<Users>

        @POST("/1.1/friendships/create.json")
        fun follow(@Query("screen_name") screen_name: String?,
                   @Query("user_id") user_id: Long?,
                   @Query("follow") follow: Boolean?): Observable<User>

        @POST("/1.1/friendships/destroy.json")
        fun unFollow(@Query("screen_name") screen_name: String?,
                     @Query("user_id") user_id: Long?): Observable<User>

        @GET("/1.1/friendships/lookup.json")
        fun lookup(@Query("screen_name") screen_name: String?,
                   @Query("user_id") user_id: Long?): Observable<List<Friendships>>
    }
}