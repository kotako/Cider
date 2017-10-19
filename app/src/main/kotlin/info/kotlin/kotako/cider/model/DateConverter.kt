package info.kotlin.kotako.cider.model

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {
        fun createdAt(createdAt: String, locale: Locale): String {
            val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ROOT).parse(createdAt)
            return SimpleDateFormat("yyyy/MM/dd HH:mm:ss", locale).format(date)
        }

        fun intervalFromCreated(createdAt: String): String {
            val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ROOT).parse(createdAt)
            val diff = (Date().time - date.time) / 1000
            return when {
                diff < 60 -> diff.toString() + "s"
                diff < 3600 -> (diff / 60).toString() + "m"
                diff < 86400 -> (diff / 3600).toString() + "h"
                else -> (diff / 86400).toString() + "d"
            }
        }
    }
}