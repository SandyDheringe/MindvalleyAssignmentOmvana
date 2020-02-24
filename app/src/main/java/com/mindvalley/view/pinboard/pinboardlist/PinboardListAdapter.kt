package com.mindvalley.view.pinboard.pinboardlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindvalley.R
import com.mindvalley.view.pinboard.dto.MasterDetails
import com.mindvalley.imageloader.callback.ContentServiceObserver
import com.mindvalley.imageloader.models.ServiceContentTypeDownload
import com.mindvalley.imageloader.models.ServiceImageDownload
import com.mindvalley.imageloader.utilities.ContentTypeServiceDownload
import com.mindvalley.view.pinboard.pinboardlist.PinboardListAdapter.UserDetailsHolder
import kotlinx.android.synthetic.main.item_photo_pin.view.*

/**
 * This adapter is used to render user pin item.
 *
 * @author SandeepD
 * @version 1.0
 */
class PinboardListAdapter(private val context: Context, private var arrlstData: ArrayList<MasterDetails>,
        private val itemActionListener: (Int) -> Unit) : RecyclerView.Adapter<UserDetailsHolder>()
{
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun getItem(position: Int): MasterDetails
    {
        return arrlstData[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailsHolder
    {
        return UserDetailsHolder(inflater.inflate(R.layout.item_photo_pin, parent, false))
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: UserDetailsHolder, position: Int)
    {
        val currentData = arrlstData[position]
        holder.bindView(currentData)
    }

    override fun getItemCount(): Int
    {
        return arrlstData.size
    }

    inner class UserDetailsHolder(view: View) : ViewHolder(view)
    {
        init
        {
            itemView.cardView.setOnClickListener {
                itemActionListener(adapterPosition)
            }
        }

        fun bindView(currentData: MasterDetails)
        {
            itemView.txtvwLikes.text = String.format("%d", currentData.likes)
            itemView.txtvwTitle.text = context.getString(R.string.clicked_by, currentData.user.name)
            if(currentData.isLikedByUser)
            {
                itemView.txtvwLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked,
                    0, 0, 0)
            } else
            {
                itemView.txtvwLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like,
                    0, 0, 0)
            }
            val mDataTypeImageCancel: ServiceContentTypeDownload =
                ServiceImageDownload(currentData.urlDetails.thumb, object : ContentServiceObserver
                {

                    override fun onSuccess(serviceContentTypeDownload: ServiceContentTypeDownload)
                    {
                        itemView.imgvwThumbnail?.setImageBitmap((serviceContentTypeDownload as ServiceImageDownload).imageBitmap)
                    }

                    override fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload,
                            statusCode: Int, errorResponse: ByteArray?, e: Throwable?)
                    {
                        itemView.imgvwThumbnail?.setImageResource(R.drawable.no_image)
                    }

                })
            ContentTypeServiceDownload.getRequest(mDataTypeImageCancel)
        }
    }

    fun add(newData: MasterDetails)
    {
        arrlstData.add(arrlstData.size, newData)
        notifyItemInserted(arrlstData.size)
    }

}