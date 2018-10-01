package com.graime.streetvoice.screens.base.ui.item

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.graime.streetvoice.screens.base.ui.adapter.BaseAdapter

class MapMarker (
        id: String,
        text: String? = null,
        var image: Bitmap? = null,
        var lat: Double,
        var lon: Double,
        var distance: Double = 0.0
) : ClusterItem, Parcelable, BaseItem(BaseAdapter.VIEW_TYPE_LOCATION, id, text.orEmpty()) {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun getSnippet(): String = titleText

    override fun getTitle(): String = titleText

    override fun getPosition(): LatLng = LatLng(lat, lon)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeParcelable(image, flags)
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapMarker> {
        override fun createFromParcel(parcel: Parcel): MapMarker {
            return MapMarker(parcel)
        }

        override fun newArray(size: Int): Array<MapMarker?> {
            return arrayOfNulls(size)
        }
    }
}