package info.kotlin.kotako.cider.model.entity

import twitter4j.DirectMessage
import java.io.Serializable

class DirectMessage(
        val id: Long, val id_str: String, val createdAt: String, val text: String,
        val sender: User, val sender_id: Long, val sender_screen_name: String,
        val recipient: User, val recipient_id: Long, val recipient_screen_name: String) : Serializable {

    constructor(id: Long, id_str: String, createdAt: String, text: String,
                sender: com.twitter.sdk.android.core.models.User, sender_id: Long, sender_screen_name: String,
                recipient: com.twitter.sdk.android.core.models.User, recipient_id: Long, recipient_screen_name: String)
            : this(id, id_str, createdAt, text, User(sender), sender_id, sender_screen_name, User(recipient), recipient_id, recipient_screen_name)

    constructor(dm: DirectMessage) : this(dm.id, dm.id.toString(), dm.createdAt.toString(), dm.text,
            User(dm.sender), dm.senderId, dm.senderScreenName, User(dm.recipient), dm.recipientId, dm.recipientScreenName)
}