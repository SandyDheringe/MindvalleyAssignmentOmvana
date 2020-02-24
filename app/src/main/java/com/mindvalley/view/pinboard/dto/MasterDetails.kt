package com.mindvalley.view.pinboard.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * pinboard response model
 * @author SandeepD
 */
class MasterDetails(
        @SerializedName("id")
        @Expose var id: String,
        @SerializedName("created_at")
        @Expose var createdAt: String,
        @SerializedName("width")
        @Expose var width: Int,
        @SerializedName("height")
        @Expose var height: Int,
        @SerializedName("color")
        @Expose var color: String,
        @SerializedName("likes")
        @Expose var likes: Int,
        @SerializedName("liked_by_user")
        @Expose var isLikedByUser: Boolean,
        @SerializedName("user")
        @Expose var user: UserDetails,
        @SerializedName("urls")
        @Expose var urlDetails: UrlDetails,
        @SerializedName("categories")
        @Expose var categories: List<CategoryDetails>? = null,
        @SerializedName("links")
        @Expose var links: LinkDetails) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(UserDetails::class.java.classLoader),
        parcel.readParcelable(UrlDetails::class.java.classLoader),
        parcel.createTypedArrayList(CategoryDetails),
        parcel.readParcelable(LinkDetails::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(id)
        parcel.writeString(createdAt)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(color)
        parcel.writeInt(likes)
        parcel.writeByte(if(isLikedByUser) 1 else 0)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(urlDetails, flags)
        parcel.writeTypedList(categories)
        parcel.writeParcelable(links, flags)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MasterDetails>
    {
        override fun createFromParcel(parcel: Parcel): MasterDetails
        {
            return MasterDetails(parcel)
        }

        override fun newArray(size: Int): Array<MasterDetails?>
        {
            return arrayOfNulls(size)
        }
    }

}