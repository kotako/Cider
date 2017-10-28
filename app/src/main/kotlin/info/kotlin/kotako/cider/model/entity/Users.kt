package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.User
import java.io.Serializable

class Users(
        val previous_cursor: Long,
        val previous_cursor_str: String,
        val next_cursor: Long,
        val users: List<User>
) : Serializable