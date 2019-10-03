package com.example.cvapp.ui



class Resoource<T>(val status: AuthStatus, val data: T?, val message: String?) {

    enum class AuthStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?): Resoource<T> {
            return Resoource(AuthStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resoource<T> {
            return Resoource(AuthStatus.ERROR, data, msg)
        }

        fun <T> loading(): Resoource<T> {
            return Resoource(AuthStatus.LOADING, null, null)
        }
    }

}