package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * links model
 * @author SandeepD
 */
data class LinkDetails(
        @SerializedName("self")
        @Expose var self: String,
        @SerializedName("html")
        @Expose var html: String,
        @SerializedName("photos")
        @Expose var photos: String,
        @SerializedName("likes")
        @Expose var likes: String) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(self)
        parcel.writeString(html)
        parcel.writeString(photos)
        parcel.writeString(likes)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LinkDetails>
    {
        override fun createFromParcel(parcel: Parcel): LinkDetails
        {
            return LinkDetails(parcel)
        }

        override fun newArray(size: Int): Array<LinkDetails?>
        {
            return arrayOfNulls(size)
        }
    }
}