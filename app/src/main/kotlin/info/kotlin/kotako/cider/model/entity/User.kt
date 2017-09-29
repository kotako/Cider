package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.User
import java.io.Serializable

class User (user:User) :Serializable {
    val contributorsEnabled = user.contributorsEnabled
    val createdAt = user.createdAt
    val defaultProfile = user.defaultProfile
    val defaultProfileImage = user.defaultProfileImage
    val description = user.description
    val email = user.email
    val favoritesCount = user.favouritesCount
    val followersCount = user.followersCount.toString()
    val followingCount = user.friendsCount.toString()
    val followRequestSent = user.followRequestSent
    val geoEnabled = user.geoEnabled
    val id = user.id
    val isTranslator = user.isTranslator
    val lang = user.lang
    val listedCount = user.listedCount
    val location = user.location
    val name = user.name
    val profileBackgroundColor = user.profileBackgroundColor
    val profileBackgroundImageUrl = user.profileBackgroundImageUrl
    val profileBackgroundTile = user.profileBackgroundTile
    val profileImageUrl = user.profileImageUrl
    val profileLinkColor = user.profileLinkColor
    val profileSidebarBorderColor = user.profileSidebarBorderColor
    val profileSidebarFillColor = user.profileSidebarFillColor
    val profileTextColor = user.profileTextColor
    val protectedUser = user.protectedUser
    val screenName = user.screenName
    val statusesCount = user.statusesCount
    val url = user.url
    val verified = user.verified
}