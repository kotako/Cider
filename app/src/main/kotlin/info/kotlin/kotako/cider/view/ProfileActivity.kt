package info.kotlin.kotako.cider.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.kotlin.kotako.cider.R

class ProfileActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}