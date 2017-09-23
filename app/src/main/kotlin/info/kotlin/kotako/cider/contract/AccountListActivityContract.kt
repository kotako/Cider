package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.Account

interface AccountListActivityContract {
    fun setAccountView(account: Account)
    fun resetAccountView()
}