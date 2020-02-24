package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Category model
 * @author SandeepD
 */
data class CategoryDetails(
        @SerializedName("id")
        @Expose var id: Int,
        @SerializedName("title")
        @Expose var title: String,
        @SerializedName("photo_count")
        @Expose var photoCount: Int,
        @SerializedName("links")
        @Expose var links: LinkDetails,
        @SerializedName("download")
        @Expose var download: String) : Parcelable
{
    constructor(parcel: Parcel) : this(parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(LinkDetails::class.java.classLoader),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(photoCount)
        parcel.writeParcelable(links, flags)
        parcel.writeString(download)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryDetails>
    {
        override fun createFromParcel(parcel: Parcel): CategoryDetails
        {
            return CategoryDetails(parcel)
        }

        override fun newArray(size: Int): Array<CategoryDetails?>
        {
            return arrayOfNulls(size)
        }
    }

}
