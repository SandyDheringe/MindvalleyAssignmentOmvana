package com.mindvalley.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.provider.Settings

/**
 * This is network utility class to check internet conditions.
 *
 * @author SandeepD
 */
object NetworkUtil
{

    fun isInternetAvailable(context: Context): Boolean
    {
        return !isAirplaneModeWithNoWIFI(context) || isNetworkAvailable(context)
    }

    /**
     * This function is used to detect whether the phone is in Airplane Mode And WIFI is turned off
     *
     * @param context
     * @return
     */
    private fun isAirplaneModeWithNoWIFI(context: Context): Boolean
    {
        return isAirplaneModeOn(context) && !isWifiEnabled(context)
    }

    private fun isWifiEnabled(context: Context): Boolean
    {
        val mng = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return mng.isWifiEnabled
    }

    private fun isNetworkAvailable(context: Context): Boolean
    {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context - The current app context
     * @return true if enabled.
     */
    private fun isAirplaneModeOn(context: Context): Boolean
    {
        return Settings.Global.getInt(context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
}