package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * User Details model
 * @author SandeepD
 */
data class UserDetails(
        @SerializedName("id")
        @Expose var id: String,
        @SerializedName("username")
        @Expose var username: String,
        @SerializedName("name")
        @Expose var name: String,
        @SerializedName("profile_image")
        @Expose var profileImage: ProfileImage,
        @SerializedName("links")
        @Expose var linkDetails: LinkDetails) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(ProfileImage::class.java.classLoader),
        parcel.readParcelable(LinkDetails::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeParcelable(profileImage, flags)
        parcel.writeParcelable(linkDetails, flags)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDetails>
    {
        override fun createFromParcel(parcel: Parcel): UserDetails
        {
            return UserDetails(parcel)
        }

        override fun newArray(size: Int): Array<UserDetails?>
        {
            return arrayOfNulls(size)
        }
    }
}