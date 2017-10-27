package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.HashtagEntity
import com.twitter.sdk.android.core.models.MediaEntity
import com.twitter.sdk.android.core.models.MentionEntity
import com.twitter.sdk.android.core.models.UrlEntity

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
                (0 until entities.size).mapTo(ArrayList()) { MediaEntity(
                    entities[it].url, entities[it].expandedURL, entities[it].displayURL, entities[it].start, entities[it].end,
                    entities[it].id, entities[it].id.toString(), entities[it].mediaURL, entities[it].mediaURLHttps, null,
                    entities[it].id, entities[it].id.toString(), entities[it].type, null, null)
        }
    }
}