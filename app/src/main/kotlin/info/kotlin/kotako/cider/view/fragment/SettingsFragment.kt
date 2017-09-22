package info.kotlin.kotako.cider.view.fragment

import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceFragment
import info.kotlin.kotako.cider.R

class SettingsFragment: PreferenceFragment(){

    companion object {
        fun newInstance(): Fragment = SettingsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}