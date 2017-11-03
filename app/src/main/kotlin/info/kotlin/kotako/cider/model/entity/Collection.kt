package info.kotlin.kotako.cider.model.entity

import java.io.Serializable

class Collection(
        val collection_type: String,
        val collection_url: String,
        val description: String,
        val name: String,
        val timeline_order: String,
        val url: String,
        val user_id: String,
        val visibility: String
): Serializable