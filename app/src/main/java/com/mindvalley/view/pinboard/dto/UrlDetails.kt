package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Url Details model
 * @author SandeepD
 */
data class UrlDetails(
        @SerializedName("raw")
        @Expose var raw: String,
        @SerializedName("full")
        @Expose var full: String,
        @SerializedName("regular")
        @Expose var regular: String,
        @SerializedName("small")
        @Expose var small: String,
        @SerializedName("thumb")
        @Expose var thumb: String) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())
    {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(raw)
        parcel.writeString(full)
        parcel.writeString(regular)
        parcel.writeString(small)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UrlDetails>
    {
        override fun createFromParcel(parcel: Parcel): UrlDetails
        {
            return UrlDetails(parcel)
        }

        override fun newArray(size: Int): Array<UrlDetails?>
        {
            return arrayOfNulls(size)
        }
    }
}