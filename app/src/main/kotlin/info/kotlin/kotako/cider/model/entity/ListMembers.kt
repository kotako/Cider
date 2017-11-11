package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.User

class ListMembers(
        val previous_cursor: Long,
        val next_cursor: Long,
        val users: List<User>
)