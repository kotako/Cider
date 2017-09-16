package info.kotlin.kotako.cider.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import info.kotlin.kotako.cider.R

class MainActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (findViewById(R.id.toolbar) as Toolbar).title = getString(R.string.app_name)
    }
}
