package com.towhid.fieldbuzz.network.error

import com.google.gson.annotations.SerializedName

class ApiError {
    @SerializedName("message")
    private var message: String? = null

    fun getMassage(): String {
        if (message.equals(null)) {
            return ""
        }
        return message!!.toString()
    }
}