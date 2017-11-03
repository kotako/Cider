package info.kotlin.kotako.cider.model.entity

import com.google.gson.JsonObject
import java.io.Serializable

class TweetCollection(
        val objects: Objects,
        val response: Response
) : Serializable {

    companion object {
        class Objects(
                val tweets: JsonObject,
                val users: JsonObject,
                val timelines: Timeline
        ) : Serializable

        class Response(
                val timeline: List<Tweet>,
                val timeline_id: String,
                val position: Position
        ) : Serializable

        class Position(
                val max_position: Long,
                val min_position: Long,
                val was_trancated: Boolean
        ) : Serializable

        class Tweet(
                val id: Long,
                val sort_index: Long
        ) : Serializable

        class Timeline(
                val collection_type: String,
                val collection_url: String,
                val description: String,
                val name: String,
                val timeline_order: String,
                val url: String,
                val user_id: String,
                val visibility: String
        ) : Serializable

    }
}