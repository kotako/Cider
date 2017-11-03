package info.kotlin.kotako.cider.model.entity

import java.io.Serializable

class CollectionPosition(
        val max_position: Long,
        val min_position: Long,
        val was_truncated: Boolean
): Serializable