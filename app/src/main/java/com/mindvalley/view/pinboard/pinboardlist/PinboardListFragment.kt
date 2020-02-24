package com.mindvalley.view.pinboard.pinboardlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mindvalley.R
import com.mindvalley.common.DialogListener
import com.mindvalley.common.FragmentInstanceHandler
import com.mindvalley.common.network.NetworkStatus
import com.mindvalley.utility.AlertUtil.showAlertDialogMultipleOptions
import com.mindvalley.utility.AlertUtil.showToast
import com.mindvalley.utility.NetworkUtil.isInternetAvailable
import com.mindvalley.view.pinboard.PinboardViewModel
import com.mindvalley.view.pinboard.PinboardViewModelFactory
import com.mindvalley.view.pinboard.details.PinDetailsFragment
import com.mindvalley.view.pinboard.dto.MasterDetails
import kotlinx.android.synthetic.main.fragment_listing.*
import java.util.*

/**
 * This Fragment is used to show the Users List.
 *
 * @author SandeepD
 * @version 1.0
 */
class PinboardListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener, DialogListener
{
    private lateinit var fragmentInstanceHandler: FragmentInstanceHandler
    private lateinit var listingAdapter: PinboardListAdapter
    private lateinit var pinboardViewModel: PinboardViewModel

    companion object
    {
        const val CLEAR_CACHE = 1

        fun newInstance(): PinboardListFragment
        {
            return PinboardListFragment()
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
        pinboardViewModel = ViewModelProvider(this, PinboardViewModelFactory()).get(PinboardViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onStart()
    {
        super.onStart()
        initObjects()
    }

    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    private fun initObjects()
    {
        swpLayUsersList.setOnRefreshListener(this)
        fabClearCache.setOnClickListener(this)
        swpLayUsersList.isEnabled = false
        pbLoading.visibility = View.VISIBLE
        //fetch user details on load
        getUserDetails()
    }

    private fun renderUserList(users: ArrayList<MasterDetails>)
    {
        listingAdapter = PinboardListAdapter(context!!, arrayListOf(), ::onItemClicked)

        rcVwUsers.adapter = listingAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        rcVwUsers.layoutManager = linearLayoutManager
        for(detailsResponse in users)
        {
            listingAdapter.add(detailsResponse)
        }
        if(swpLayUsersList.isRefreshing) swpLayUsersList.isRefreshing = false
        swpLayUsersList.isEnabled = true
        pbLoading.visibility = View.GONE
    }

    private fun displayErrorMessage(errorMessage: String?)
    {
        showToast(activity, errorMessage)
        swpLayUsersList.isEnabled = true
    }

    private fun onItemClicked(position: Int)
    {
        val currentData = listingAdapter.getItem(position)

        if(!isInternetAvailable(context!!))
        {
            showToast(activity, getString(R.string.network_error))
        } else
        {
            fragmentInstanceHandler.changeFragment(this@PinboardListFragment, PinDetailsFragment.newInstance(currentData), true)
        }
    }

    override fun onRefresh()
    {
        getUserDetails()

    }

    private fun getUserDetails()
    {
        if(!isInternetAvailable(context!!))
        {
            displayErrorMessage(getString(R.string.network_error))
            return
        }

        pinboardViewModel.getUserDetails().observe(this, androidx.lifecycle.Observer {

            if(it.networkStatus == NetworkStatus.SUCCESS)
            {
                it.result?.let { it1 -> renderUserList(it1) }
            } else
                displayErrorMessage(getString(R.string.str_error_loading_user))
        })
    }

    override fun onClick(view: View)
    {
        if(view === fabClearCache)
        {
            showAlertDialogMultipleOptions(activity, CLEAR_CACHE, this,
                getString(R.string.clear_cache), getString(R.string.clear_cache_description),
                getString(R.string.yes), getString(R.string.no))
        }
    }

    override fun onPositiveAction(dialogID: Int, updatedData: Any?)
    {
        pinboardViewModel.clearCache()
        showToast(context, getString(R.string.str_cache_cleared))
        getUserDetails()
    }

    override fun onNegativeAction(dialogID: Int, updatedData: Any?)
    {
    }

}