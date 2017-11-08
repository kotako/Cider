package info.kotlin.kotako.cider.model.entity

import twitter4j.DirectMessage
import java.io.Serializable

class DirectMessage(
        val id: Long, val id_str: String, val created_at: String, val text: String,
        val sender: User, val sender_id: Long, val sender_screen_name: String,
        val recipient: User, val recipient_id: Long, val recipient_screen_name: String) : Serializable {

    constructor(dm: DirectMessage) : this(dm.id, dm.id.toString(), dm.createdAt.toString(), dm.text,
            User(dm.sender), dm.senderId, dm.senderScreenName, User(dm.recipient), dm.recipientId, dm.recipientScreenName)
}