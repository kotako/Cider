package info.kotlin.kotako.cider.model.entity

import com.google.gson.JsonObject
import com.twitter.sdk.android.core.models.Tweet

class SearchResponse(
        val statuses: List<Tweet>,
        val search_metadata: JsonObject
)