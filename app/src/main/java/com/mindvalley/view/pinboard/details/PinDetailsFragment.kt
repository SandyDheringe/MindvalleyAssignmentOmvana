package com.mindvalley.view.pinboard.details

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindvalley.R
import com.mindvalley.common.FragmentInstanceHandler
import com.mindvalley.view.pinboard.dto.CategoryDetails
import com.mindvalley.view.pinboard.dto.MasterDetails
import com.mindvalley.view.pinboard.dto.UserDetails
import com.mindvalley.imageloader.callback.ContentServiceObserver
import com.mindvalley.imageloader.models.ServiceContentTypeDownload
import com.mindvalley.imageloader.models.ServiceImageDownload
import com.mindvalley.imageloader.utilities.ContentTypeServiceDownload
import com.mindvalley.utility.AlertUtil.showToast
import com.mindvalley.utility.DateTimeUtil.formattedDateFromDate
import com.mindvalley.utility.DateTimeUtil.getDateFromString
import com.mindvalley.utility.NetworkUtil
import com.mindvalley.view.pinboard.unsplash.UnsplashAccountFragment
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * This Fragment is used to show photo details
 *
 * @author SandeepD
 * @version 1.0
 */
class PinDetailsFragment : Fragment(), View.OnClickListener
{
    private lateinit var fragmentInstanceHandler: FragmentInstanceHandler
    private var masterDetails: MasterDetails? = null

    companion object
    {
        const val ARG_DATA = "data"
        fun newInstance(masterDetails: MasterDetails?): PinDetailsFragment
        {
            return PinDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DATA, masterDetails)
                }
            }
        }
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        fragmentInstanceHandler = activity as FragmentInstanceHandler
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if(bundle != null)
        {
            masterDetails = bundle.getParcelable(
                ARG_DATA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initObjects()
    }


    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    private fun initObjects()
    {
        btnViewProfile.setOnClickListener(this)
        renderDetails(masterDetails)
    }


    override fun onClick(view: View)
    {
        if(view === btnViewProfile)
        {
            if(!NetworkUtil.isInternetAvailable(context!!))
            {
                showToast(activity, getString(R.string.network_error))
                fragmentInstanceHandler.changeFragment(this@PinDetailsFragment,
                    UnsplashAccountFragment.newInstance(masterDetails!!.user.linkDetails.html),
                    true)
            } else
            {
                fragmentInstanceHandler.changeFragment(this@PinDetailsFragment,
                    UnsplashAccountFragment.newInstance(masterDetails!!.user.linkDetails.html),
                    true)
            }
        }
    }

    private fun renderDetails(currentData: MasterDetails?)
    {
        if(currentData != null)
        {
            renderPhoto(currentData.urlDetails.thumb)
            setBackgroundColor(currentData.color)
            renderUserDetails(currentData.user)
            renderPhotoDetails(currentData)
            renderCategories(getDisplayCategories(currentData.categories))
        }

    }

    private fun renderPhoto(thumbURL: String)
    {
        val mDataTypeImageCancel: ServiceContentTypeDownload =
            ServiceImageDownload(thumbURL, object : ContentServiceObserver
            {
                override fun onSuccess(serviceContentTypeDownload: ServiceContentTypeDownload)
                {
                    if(isVisible)
                        imgvwFullView.setImageBitmap((serviceContentTypeDownload as ServiceImageDownload).imageBitmap)
                }

                override fun onFailure(serviceContentTypeDownload: ServiceContentTypeDownload,
                        statusCode: Int, errorResponse: ByteArray?, e: Throwable?)
                {
                    if(isVisible)
                        imgvwFullView.setImageResource(R.drawable.no_image)
                }
            })
        ContentTypeServiceDownload.getRequest(mDataTypeImageCancel)
    }

    private fun setBackgroundColor(color: String?)
    {
        linlayParent.setBackgroundColor(Color.parseColor(color))
    }

    private fun renderUserDetails(user: UserDetails)
    {
        txtvwTitle.text = getString(R.string.clicked_by, user.name)
    }

    private fun renderPhotoDetails(currentData: MasterDetails)
    {
        txtvwLikes.text = String.format("%d", currentData.likes)
        txtvwImageDimensions.text = getString(R.string.str_img_dim, currentData.width, currentData.height)
        txtvwImagePublishedDate.text = getString(R.string.str_creattion_date,
            formattedDateFromDate(getDateFromString("yyyy-MM-dd'T'HH:mm:ssXXX",
                masterDetails?.createdAt), "dd-MM-yyyy"))
    }

    private fun renderCategories(displayCategories: String?)
    {
        txtvwImageCategory.text = getString(R.string.str_img_category, displayCategories)
    }

    private fun getDisplayCategories(categories: List<CategoryDetails>?): String
    {
        var displayCategories = ""
        for((_, title, photoCount) in categories!!)
        {
            displayCategories = if(displayCategories.trim { it <= ' ' } == "")
            {
                String.format("%s (%d)", title, photoCount)
            } else
            {
                String.format("%s/%s",
                    displayCategories,
                    String.format("%s (%d)", title, photoCount))
            }
        }
        return displayCategories
    }

}