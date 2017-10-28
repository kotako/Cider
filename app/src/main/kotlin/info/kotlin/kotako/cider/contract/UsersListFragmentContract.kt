package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.User


interface UsersListFragmentContract {
    fun startProfileActivity(userId: Long)
    fun addUser(user: User)
    fun addUserList(users: List<User>)
    fun usersListSize(): Int
    fun clearUsersList()
    fun showProgressBar()
    fun hideProgressBar()
}