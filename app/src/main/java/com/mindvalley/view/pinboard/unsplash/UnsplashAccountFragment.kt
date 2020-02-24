package com.mindvalley.view.pinboard.unsplash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mindvalley.R
import kotlinx.android.synthetic.main.fragment_unsplash.*

/**
 * This fragment show unsplash account of the user.
 *
 * @author SandeepD
 * @version 1.0
 */
class UnsplashAccountFragment : Fragment()
{
    var webURL: String? = null

    companion object
    {
        const val ARG_URL = "arg_url"
        fun newInstance(url: String?): UnsplashAccountFragment
        {
            return UnsplashAccountFragment().apply {
                arguments =  Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if(bundle != null)
        {
            webURL = bundle.getString(ARG_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_unsplash, container, false)
    }

    override fun onStart()
    {
        super.onStart()
        initObjects()
    }

    /**
     * This function is used to initialise the objects that are going to be used in this fragment
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun initObjects()
    {
        wvDisplay.loadUrl(webURL)
        // Enable Javascript
        wvDisplay.settings.apply {
            javaScriptEnabled = true
        }
        // Force links and redirects to open in the WebView instead of in a browser
        wvDisplay.webViewClient = WebViewClient()
    }

}