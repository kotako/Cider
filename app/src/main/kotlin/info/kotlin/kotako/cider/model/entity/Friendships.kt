package info.kotlin.kotako.cider.model.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import info.kotlin.kotako.cider.BR
import java.io.Serializable

class Friendships(
        val name: String?,
        val screen_name: String?,
        val id: Long?,
        val id_str: String?,
        val connections: List<String>) : Serializable, BaseObservable() {

    @get:Bindable
    var following = connections.contains("following")
        set(value) {
            field = value
            notifyPropertyChanged(BR.following)
        }
    @get:Bindable
    var followed_by = connections.contains("followed_by")
        set(value) {
            field = value
            notifyPropertyChanged(BR.followed_by)
        }
    @get:Bindable
    var following_requested = connections.contains("following_requested")
        set(value) {
            field = value
            notifyPropertyChanged(BR.following_requested)
        }

    @get:Bindable
    var none = connections.contains("none")
        set(value) {
            field = value
            notifyPropertyChanged(BR.none)
        }

    @get:Bindable
    var blocking = connections.contains("blocking")
        set(value) {
            field = value
            notifyPropertyChanged(BR.blocking)
        }

    @get:Bindable
    var muting = connections.contains("muting")
        set(value) {
            field = value
            notifyPropertyChanged(BR.muting)
        }
}