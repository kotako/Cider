package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.Account

interface AccountListActivityContract {
    var accountChanged: Boolean
    fun setAccountView(account: Account)
    fun resetAccountView()
}