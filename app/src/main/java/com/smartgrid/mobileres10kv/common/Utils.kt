package com.graime.streetvoice.common.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

object Utils {


    fun convertDpToPx(dp: Int, context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
        return Math.round(pixels)
    }

    fun hideSoftKeyboard(activity: Activity?) {
        try {
            val inputMethodManager = activity?.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as? InputMethodManager?
            inputMethodManager?.hideSoftInputFromWindow(
                    activity?.currentFocus?.windowToken, 0)
        } catch (ignored: Exception) {
        }

    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean {

        var hasAllPermissions = true

        for (permission in permissions) {
            //you can return false instead of assigning, but by assigning you can log all permission values
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false
            }
        }

        return hasAllPermissions

    }


    fun hasPermission(context: Context, permission: String): Boolean {
        val res = context.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED

    }

    fun servicesConnected(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val api = GoogleApiAvailability.getInstance()

        return ConnectionResult.SUCCESS == api.isGooglePlayServicesAvailable(context)
    }

    fun makeStringFromInputStream(inputStreamReader: InputStreamReader): String? {
        val cycleBuilder = StringBuilder()
        val builder = StringBuilder()
        val reader = BufferedReader(inputStreamReader)

        val bf = true

        try {
            cycleBuilder.append(reader.readLine())
            while (bf) {
                if (cycleBuilder.length == 4
                        && cycleBuilder[0] == 'n'
                        && cycleBuilder[1] == 'u'
                        && cycleBuilder[2] == 'l'
                        && cycleBuilder[3] == 'l') {
                    cycleBuilder.setLength(0)
                    break
                }

                builder.append(cycleBuilder).append("\n")
                cycleBuilder.setLength(0)
                cycleBuilder.append(reader.readLine())
            }

            reader.close()
            inputStreamReader.close()
        } catch (e: IOException) {
            builder.setLength(0)
        }

        return if (builder.isEmpty()) null else builder.toString()
    }

    fun toMillis(unix: Long): Long {
        val date = Date(unix * 1000L)
        return date.time
    }

    fun fromMillis(millisecond: Long): Long {
        return millisecond / 1000L
    }
}