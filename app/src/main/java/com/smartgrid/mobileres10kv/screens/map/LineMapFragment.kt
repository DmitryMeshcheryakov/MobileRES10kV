package com.smartgrid.mobileres10kv.screens.map

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.maps.MapView
import com.graime.streetvoice.modules.location.LocationHelper
import com.graime.streetvoice.screens.base.ui.fragments.BaseMapFragment
import com.smartgrid.mobileres10kv.R
import kotlinx.android.synthetic.main.fragment_line_map.*

class LineMapFragment : BaseMapFragment(), MapLineView {

    companion object {
        const val TAG = "MapFragment"

        fun instance(): LineMapFragment {
            return LineMapFragment()
        }
    }

    override fun tag(): String {
        return TAG
    }

    @InjectPresenter
    lateinit var presenter: MapLinePresenter

    override fun getMapView(): MapView? = mapView

    override fun getLocationHelper(): LocationHelper? = presenter.location

  //  override fun getMarkerClickListener(): ClusterManager.OnClusterItemClickListener<MapMarker> = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_line_map, container, false)
    }

    override fun showMyPosition(show: Boolean, moveCamera: Boolean) {
        presenter.checkPermissions(activity,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION) {
            super.showMyPosition(show, moveCamera)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}