package com.example.cvapp.ui


class Resource<T>(val status: AuthStatus, val data: T?, val message: String?) {

    enum class AuthStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(AuthStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(AuthStatus.ERROR, data, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(AuthStatus.LOADING, null, null)
        }
    }

}