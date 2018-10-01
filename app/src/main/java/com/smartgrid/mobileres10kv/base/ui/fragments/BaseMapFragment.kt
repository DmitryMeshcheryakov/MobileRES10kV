package com.graime.streetvoice.screens.base.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.*
import com.graime.streetvoice.modules.location.LocationHelper
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener
import com.graime.streetvoice.screens.base.ui.item.MapMarker
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import com.smartgrid.mobileres10kv.base.ClusterRenderer

abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback {

    var showMyPositionOnStart: Boolean = true

    protected var map: GoogleMap? = null

    private var clusterManager: ClusterManager<MapMarker>? = null
    
    abstract fun getMapView() : MapView?

    abstract fun getLocationHelper() : LocationHelper?

    open fun getMarkerClickListener() : ClusterManager.OnClusterItemClickListener<MapMarker> =
            OnClusterItemClickListener { true }

    protected open fun showMyPosition(show: Boolean = true, moveCamera: Boolean = true) {
        try {
            map?.isMyLocationEnabled = show
            if (moveCamera)
                moveToPosition()
        } catch (e: SecurityException) {

        }
    }

    protected fun enableCompass(enable: Boolean = true) {
        map?.uiSettings?.isCompassEnabled = enable
    }

    protected fun moveToPosition(lat: Double = getLocationHelper()?.currentLatitude ?: 0.0, lon: Double = getLocationHelper()?.currentLongitude ?: 0.0) {
        val mark = LatLng(lat, lon)
        val  cameraPosition = CameraPosition.builder().target(mark).zoom(15f).build()
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    protected fun clearMarkers() {
        clusterManager?.clearItems()
    }

    protected fun addMarkers(markers: List<MapMarker>?) {
        markers?.let {mapMarkers->
            clusterManager?.addItems(mapMarkers)
            clusterManager?.cluster()
        }
    }

    protected fun addMarker(marker: MapMarker?, moveCamera: Boolean = false) {
        marker?.let {mapMarker->
            clusterManager?.addItem(mapMarker)
            clusterManager?.cluster()
            if (moveCamera)
                moveToPosition(marker.lat, marker.lon)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMapView()?.onCreate(savedInstanceState)
        getMapView()?.getMapAsync(this)
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.uiSettings?.isMyLocationButtonEnabled = true
        map?.uiSettings?.setAllGesturesEnabled(true)

        context?.let {
            clusterManager = ClusterManager(it, p0)
            if (p0 != null && clusterManager != null) {
                clusterManager?.renderer = ClusterRenderer(it, p0, clusterManager!!)
                clusterManager?.algorithm = NonHierarchicalDistanceBasedAlgorithm<MapMarker>()
                clusterManager?.setAnimation(true)
                clusterManager?.setOnClusterItemClickListener(getMarkerClickListener())
                p0.setOnCameraIdleListener(clusterManager)
                p0.setOnMarkerClickListener(clusterManager)
                p0.setOnInfoWindowClickListener(clusterManager)
            }
        }
        if (showMyPositionOnStart)
            showMyPosition()
    }

    override fun onResume() {
        getMapView()?.onResume()
        getLocationHelper()?.connect()
        super.onResume()
    }

    override fun onPause() {
        getMapView()?.onPause()
        getLocationHelper()?.disconnect()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        getMapView()?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
       getMapView()?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroy() {
        getMapView()?.onDestroy()
        super.onDestroy()
    }
}