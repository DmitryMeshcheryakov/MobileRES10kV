package com.graime.streetvoice.modules.preferences

import android.content.SharedPreferences

class PreferencesHelper(private val preferences: SharedPreferences) {

    fun putString(key: String, value: String): Boolean {
        return this.preferences.edit().putString(key, value).commit()
    }

    fun getString(key: String): String {
        return this.preferences.getString(key, "")
    }

    fun putBool(key: String, value: Boolean): Boolean {
        return this.preferences.edit().putBoolean(key, value).commit()
    }

    fun getBool(key: String): Boolean {
        return this.preferences.getBoolean(key, false)
    }

    fun clear(): Boolean {
        return this.preferences.edit().clear().commit()
    }
}