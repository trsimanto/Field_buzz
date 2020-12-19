package com.towhid.fieldbuzz.shared_pref

import android.content.Context
import android.content.SharedPreferences
import com.towhid.fieldbuzz.util.LoginStatus

class SharedPref(context: Context) {
    var mySharedPref: SharedPreferences
    var loginStatus: String?
        get() = mySharedPref.getString("loginStatus", LoginStatus.login.toString())
        set(loginStatus) {
            val editor = mySharedPref.edit()
            editor.putString("loginStatus", loginStatus!!)
            editor.apply()
        }
    var token: String?
        get() = mySharedPref.getString("token", "")
        set(token) {
            val editor = mySharedPref.edit()
            editor.putString("token", token)
            editor.apply()
        }
    var fileTokenId: Int
        get() = mySharedPref.getInt("fileTokenId", 0)
        set(fileTokenId) {
            val editor = mySharedPref.edit()
            editor.putInt("fileTokenId", fileTokenId)
            editor.apply()
        }
    var uuid: String?
        get() = mySharedPref.getString("uuid", "")
        set(uuid) {
            val editor = mySharedPref.edit()
            editor.putString("uuid", uuid)
            editor.apply()
        }
    fun setDeny(deny: Boolean) {
        val editor = mySharedPref.edit()
        editor.putBoolean("deny", deny)
        editor.apply()
    }

    fun getDeny(): Boolean {
        return mySharedPref.getBoolean("deny", false)
    }
    init {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }
}
