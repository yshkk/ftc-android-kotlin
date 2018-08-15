package com.ft.ftchinese.models

import android.content.Context
import android.util.Log
import com.ft.ftchinese.util.gson
import com.google.gson.JsonSyntaxException

data class User(
        val id: String,
        val name: String?,
        val email: String,
        val avatar: String?,
        val isVip: Boolean,
        val verified: Boolean,
        val membership: Membership
) {
    fun save(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("cookie", gson.toJson(this))
                .apply()
    }

    companion object {
        private const val PREFERENCE_NAME = "user"
        private const val USER_COOKIE_KEY = "cookie"
        private const val TAG = "User"

        fun loadFromPref(context: Context): User? {
            val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val cookie = sharedPreferences.getString(USER_COOKIE_KEY, null) ?: return null

            return try { gson.fromJson<User>(cookie, User::class.java) }
            catch (e: JsonSyntaxException) {
                Log.i(TAG, e.toString())

                null
            }
        }

        fun removeFromPref(context: Context) {
            val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.remove(USER_COOKIE_KEY).apply()
        }
    }
}

data class Membership(
        val type: String,
        val startAt: String?,
        val expireAt: String?
)