package com.towhid.fieldbuzz.network.model.sign_in.response


class SignInRes (
        private var token: String
) {
    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        if (token.equals(null)) {
            return ""
        }
        return token
    }

}