package com.smartgrid.mobileres10kv.base.models

import android.os.Parcel
import android.os.Parcelable

data class BSItem(
        var id: Int,
        var text: String,
        var iconId: Int = -1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
        parcel.writeInt(iconId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BSItem> {
        override fun createFromParcel(parcel: Parcel): BSItem {
            return BSItem(parcel)
        }

        override fun newArray(size: Int): Array<BSItem?> {
            return arrayOfNulls(size)
        }
    }
}