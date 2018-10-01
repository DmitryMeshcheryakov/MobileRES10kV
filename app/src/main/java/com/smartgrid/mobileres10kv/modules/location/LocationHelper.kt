package com.graime.streetvoice.modules.location

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.smartgrid.mobileres10kv.common.exceptions.LocationException

class LocationHelper(var context: Context):
        LocationListener {

    companion object {
        const val TAG = "LOCATION"
        const val MIN_UPDATE_TIME = 10000L //ms
        const val MIN_UPDATE_DISTANCE = 10f //km

        const val CODE_NO_PROVIDER = 0
    }

    var locationErrorListener: ((t: Throwable)->Unit)? = null
    var locationChangeListener: ((lat: Double, lon: Double)->Unit)? = null
    var currentLatitude = 0.0
    var currentLongitude = 0.0

    var geocoder: Geocoder? = Geocoder(context)

    private var locationManager: LocationManager? = null

    init {
        geocoder = Geocoder(context)
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    fun connect() {
        Log.d(TAG, "try to connect")
        //googleApiClient?.connect()
        try {
            when {
                locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true -> {
                    locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this)
                    Log.d(TAG, "connected to GPS_PROVIDER")
                }
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true -> {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this)
                    Log.d(TAG, "connected to NETWORK_PROVIDER")
                }
                else ->
                    throw LocationException(CODE_NO_PROVIDER, "no provider")
            }
            saveNewLocation(locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER))
        } catch (e: SecurityException) {
            locationErrorListener?.invoke(e)
        } catch (e: Exception) {
            locationErrorListener?.invoke(e)
        }
    }

    fun disconnect() {
        Log.d(TAG, "disconnect")
        locationManager?.removeUpdates(this)
    }

    private fun saveNewLocation(location: Location?) {
        currentLatitude = location?.latitude ?: 0.0
        currentLongitude = location?.longitude ?: 0.0
        locationChangeListener?.invoke(currentLatitude, currentLongitude)
        Log.d(TAG, "new location $currentLatitude : $currentLongitude")
    }

    override fun onLocationChanged(p0: Location?) {
        saveNewLocation(p0)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.d(TAG, "onStatusChanged $p0")
    }

    override fun onProviderEnabled(p0: String?) {
        connect()
    }

    override fun onProviderDisabled(p0: String?) {
        connect()
    }
}