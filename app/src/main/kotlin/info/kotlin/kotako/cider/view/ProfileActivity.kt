package info.kotlin.kotako.cider.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.kotlin.kotako.cider.R

class ProfileActivity: AppCompatActivity(){

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, ProfileActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}