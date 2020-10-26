package com.websoftquality.meetfriends.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.websoftquality.meetfriends.models.retrofitModel.responses.retrofitResponse.LoginResponse

private const val PREFS_FILE_NAME = "MeetFriends"
private const val PREFS_IS_LOGIN = "isLogin"
private const val PREFS_IS_REMEMBER = "isRemember"
private const val PREFS_DEVICE_CONFIG = "device data"
private const val  PREFS_IS_PROFILE_SUBMIT = "isProfileSubmitted"

class UserPreferences (context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()

    fun clearPrefs() {
        mEditor.apply {
            clear()
            commit()
        }
    }

    fun isAlreadyLogin(): Boolean {
        return sharedPreferences.getBoolean(PREFS_IS_LOGIN, false)
    }

    fun isFinishedProfile(): Boolean {
        return sharedPreferences.getBoolean(PREFS_IS_PROFILE_SUBMIT, false)
    }

    fun setLogin(b: Boolean) {
        mEditor.putBoolean(PREFS_IS_LOGIN, b)
        mEditor.apply()
    }

    fun setRemeber(s: Boolean) {
        mEditor.putBoolean(PREFS_IS_REMEMBER, s)
        mEditor.apply()
    }

    fun setLoginData(data: LoginResponse) {
        mEditor.putString(PREFS_DEVICE_CONFIG, Gson().toJson(data))
        mEditor.apply()
    }

    fun getDeviceData(): LoginResponse? {
        return Gson().fromJson(sharedPreferences.getString(PREFS_DEVICE_CONFIG, ""), LoginResponse::class.java)
    }

    fun setProfileUpdate(b: Boolean) {
        mEditor.putBoolean(PREFS_IS_PROFILE_SUBMIT, b)
        mEditor.apply()
    }
}