package com.starsep.sokoban.release.fragment

import android.content.SharedPreferences
import android.preference.*
import androidx.fragment.app.Fragment
// import com.starsep.sokoban.release.settings.AppCompatPreferenceActivity

/**
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
// AppCompatPreferenceActivity(),
class SettingsFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        // TODO: Fix Settings
    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_settings)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        initSummary(preferenceScreen)
        preferenceScreen
                .sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        updatePrefSummary(findPreference(key))
    }

    private fun updatePrefSummary(preference: Preference) {
        if (preference is EditTextPreference) {
            preference.summary = preference.text
        }
        if (preference is ListPreference) {
            preference.summary = preference.entry
        }
    }

    private fun initSummary(p: Preference) {
        if (p is PreferenceGroup) {
            for (i in 0 until p.preferenceCount) {
                initSummary(p.getPreference(i))
            }
        } else {
            updatePrefSummary(p)
        }
    }

    *//**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     *//*
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
    }

    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_settings)
            setHasOptionsMenu(true)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }*/
}
