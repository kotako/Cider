package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.User
import java.io.Serializable

class User(val contributorsEnabled: Boolean?, val createdAt: String, val defaultProfile: Boolean,
           val defaultProfileImage: Boolean, val description: String, val favoritesCount: Int,
           val followersCount: Int, val followingCount: Int, val followRequestSent: Boolean,
           val geoEnabled: Boolean, val id: Long, val lang: String, val listedCount: Int, val location: String?,
           val name: String, val profileBackgroundColor: String?, val profileBackgroundImageUrl: String?,
           val profileBackgroundTile: Boolean?, val profileImageUrl: String, val profileLinkColor: String?,
           val profileSidebarBorderColor: String?, val profileSidebarFillColor: String?,
           val profileBannerUrl: String?, val profileTextColor: String?, val protectedUser: Boolean,
           val screenName: String, val statusCount: Int, val url: String?, val verified: Boolean) : Serializable {

    constructor(user: User) : this(user.contributorsEnabled, user.createdAt, user.defaultProfile,
            user.defaultProfileImage, user.description, user.favouritesCount, user.followersCount,
            user.friendsCount, user.followRequestSent, user.geoEnabled, user.id, user.lang, user.listedCount,
            user.location, user.name, user.profileBackgroundColor, user.profileBackgroundImageUrl,
            user.profileBackgroundTile, user.profileImageUrl, user.profileLinkColor, user.profileSidebarBorderColor,
            user.profileSidebarFillColor, user.profileBannerUrl, user.profileTextColor, user.protectedUser,
            user.screenName, user.statusesCount, user.url, user.verified)

    constructor(user: twitter4j.User) : this(user.isContributorsEnabled, user.createdAt.toString(),
            user.isDefaultProfile, user.isDefaultProfileImage, user.description,
            user.favouritesCount, user.followersCount, user.friendsCount, user.isFollowRequestSent,
            user.isGeoEnabled, user.id, user.lang, user.listedCount, user.location, user.name,
            user.profileBackgroundColor, user.profileBackgroundImageURL, user.isProfileBackgroundTiled,
            user.profileImageURL, user.profileLinkColor, user.profileSidebarBorderColor, user.profileSidebarFillColor,
            user.profileBannerURL, user.profileTextColor, user.isProtected, user.screenName, user.statusesCount,
            user.url, user.isVerified)
}