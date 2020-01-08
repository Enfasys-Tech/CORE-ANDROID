package com.enfasys.android.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.enfasys.core.NetworkVerifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNetworkVerifier @Inject constructor(appContext: Context) : NetworkVerifier {
    private val connectivityManager: ConnectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnectionAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}