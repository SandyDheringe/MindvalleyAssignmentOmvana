package com.mindvalley.view.pinboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mindvalley.R
import com.mindvalley.common.FragmentInstanceHandler
import com.mindvalley.view.pinboard.pinboardlist.PinboardListFragment

/**
 * Pinboard activity to show pin wall
 *
 * @author Sandeep
 */
class PinboardActivity : AppCompatActivity(), FragmentInstanceHandler
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinboard)
        changeFragment(null, PinboardListFragment.newInstance(), false)
    }

    /**
     * This function is used to get the current Top Fragment from the Back stack
     *
     * @return - The Fragment on the Top
     */
    private val currentTopFragment: Fragment?
        get()
        {
            val fragmentManager = supportFragmentManager
            //Performs any previous pending operations in the queue for the fragments
            fragmentManager.executePendingTransactions()
            val fragmentTag =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
            return fragmentManager.findFragmentByTag(fragmentTag)
        }


    override fun changeFragment(fromFragment: Fragment?,
                                toFragment: Fragment,
                                addToBackStack: Boolean)
    {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        // ft.setCustomAnimations(R.animator.custom_fade_in, R.animator.custom_fade_out, R.animator.custom_fade_in, R.animator.custom_fade_out);
        ft.replace(R.id.fragmentHolder, toFragment, toFragment.javaClass.name)
        if(addToBackStack && fromFragment != null)
        {
            ft.addToBackStack(fromFragment.javaClass.name) // we are following standard practice of giving fromFrag class name as name for the back stack state.
        }
        ft.commitAllowingStateLoss()
    }
}