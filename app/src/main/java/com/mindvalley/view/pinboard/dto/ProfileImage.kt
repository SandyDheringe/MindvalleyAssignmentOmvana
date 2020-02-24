package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * profile image details model
 * @author SandeepD
 */
data class ProfileImage(
        @SerializedName("small")
        @Expose var small: String,
        @SerializedName("medium")
        @Expose var medium: String,
        @SerializedName("large")
        @Expose var large: String) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(small)
        parcel.writeString(medium)
        parcel.writeString(large)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileImage>
    {
        override fun createFromParcel(parcel: Parcel): ProfileImage
        {
            return ProfileImage(parcel)
        }

        override fun newArray(size: Int): Array<ProfileImage?>
        {
            return arrayOfNulls(size)
        }
    }
}