package com.example.cityquest.util

import android.os.Parcel
import android.os.Parcelable

data class ImageTile(val byteArray: ByteArray) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createByteArray() ?: byteArrayOf())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByteArray(byteArray)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageTile

        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        return byteArray.contentHashCode()
    }

    companion object CREATOR : Parcelable.Creator<ImageTile> {
        override fun createFromParcel(parcel: Parcel): ImageTile {
            return ImageTile(parcel)
        }

        override fun newArray(size: Int): Array<ImageTile?> {
            return arrayOfNulls(size)
        }
    }
}

