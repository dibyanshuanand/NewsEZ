package com.dibanand.newsez.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI

object NetworkConnectionManager {
    fun isInternetAvailable(application: Application): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            networkCapabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}