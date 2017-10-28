package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.*

import twitter4j.UserMentionEntity
import java.io.Serializable

class TweetExtended() : Serializable {
    companion object {
        fun toHashTagEntityList(entities: Array<twitter4j.HashtagEntity>): List<HashtagEntity> =
                (0 until entities.size).mapTo(ArrayList()) { HashtagEntity(entities[it].text, entities[it].start, entities[it].end) }

        fun toUrlEntityList(entities: Array<twitter4j.URLEntity>): List<UrlEntity> =
                (0 until entities.size).mapTo(ArrayList()) { UrlEntity(entities[it].url, entities[it].expandedURL, entities[it].displayURL, entities[it].start, entities[it].end) }

        fun toMentionEntityList(entities: Array<UserMentionEntity>): List<MentionEntity> =
                (0 until entities.size).mapTo(ArrayList()) { MentionEntity(entities[it].id, entities[it].id.toString(), entities[it].name, entities[it].screenName, entities[it].start, entities[it].end) }

        fun toMediaEntityList(entities: Array<twitter4j.ExtendedMediaEntity>): List<MediaEntity> =
                (0 until entities.size).mapTo(ArrayList()) {
                    MediaEntity(
                            entities[it].url, entities[it].expandedURL, entities[it].displayURL, entities[it].start, entities[it].end,
                            entities[it].id, entities[it].id.toString(), entities[it].mediaURL, entities[it].mediaURLHttps,
                            MediaEntity.Sizes(
                                    MediaEntity.Size(entities[it].sizes[twitter4j.MediaEntity.Size.THUMB]!!.width,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.THUMB]!!.height,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.THUMB]!!.resize.toString()),
                                    MediaEntity.Size(entities[it].sizes[twitter4j.MediaEntity.Size.SMALL]!!.width,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.SMALL]!!.height,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.SMALL]!!.resize.toString()),
                                    MediaEntity.Size(entities[it].sizes[twitter4j.MediaEntity.Size.MEDIUM]!!.width,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.MEDIUM]!!.height,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.MEDIUM]!!.resize.toString()),
                                    MediaEntity.Size(entities[it].sizes[twitter4j.MediaEntity.Size.LARGE]!!.width,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.LARGE]!!.height,
                                            entities[it].sizes[twitter4j.MediaEntity.Size.LARGE]!!.resize.toString())),
                            entities[it].id, entities[it].id.toString(), entities[it].type, null, null)
                }
    }
}