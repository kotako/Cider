package info.kotlin.kotako.cider.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import info.kotlin.kotako.cider.R

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, SettingsActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setUpView()
    }

    fun setUpView() {
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fragmentManager.beginTransaction()
                .replace(R.id.layout_settings, SettingsFragment.newInstance())
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}