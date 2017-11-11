package info.kotlin.kotako.cider.model.entity

import java.io.Serializable

class UserList(
        val slug: String, val name: String, val created_at: String, val uri: String, val subscriber_count: Int,
        val id_str: String, val member_count: Int, val mode: String, val id: Long, val full_name: String,
        val description: String, val user: User, val following: Boolean) : Serializable