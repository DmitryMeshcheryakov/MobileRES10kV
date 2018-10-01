package com.smartgrid.mobileres10kv.base

import android.content.Context
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.graime.streetvoice.screens.base.ui.item.MapMarker
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.ui.IconGenerator
import com.smartgrid.mobileres10kv.R


class ClusterRenderer(private val context: Context, map: GoogleMap, clusterManager: ClusterManager<MapMarker>) :
        DefaultClusterRenderer<MapMarker>(context, map, clusterManager) {

    private val clusterIconGenerator: IconGenerator = IconGenerator(context)

    override fun onBeforeClusterItemRendered(item:MapMarker?, markerOptions: MarkerOptions?) {
        val markerDescriptor = BitmapDescriptorFactory.fromBitmap(item?.image)
        markerOptions?.icon(markerDescriptor)?.snippet(item?.title)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MapMarker>, markerOptions: MarkerOptions) {
        clusterIconGenerator.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_circle_colored))
        clusterIconGenerator.setTextAppearance(R.style.AppTheme)
        val icon = clusterIconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }
}