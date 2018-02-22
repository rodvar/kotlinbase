package com.rodvar.kotlinbase.base.presentation

import android.content.Context
import com.rodvar.kotlinbase.R

/**
 * Created by rodvar on 5/9/17.
 *
 * Behaviour for any view in the app
 */
interface AppView {

    companion object {
        val NO_VIEW = 0
    }

    val title: Int

    /**
     * @return the app context for this view
     */
    @Throws(NoContextException::class)
    fun context(): Context

    /**
     * Loads fragmentToLoad into the view with id containerId.
     *
     * @param fragmentToLoad the fragment to replace the container view
     * @param containerId the id of the view that it's going to contain this fragment
     * @param addToBackStack true if it should be added to the backstack, false otherwise
     * @param addToBackStackTag the tag to use for backstacking this fragment, null otherwise
     * @param animate true for default animation, false otherwise
     */
    fun loadFragment(containerId: Int = R.id.fragment_container, fragmentToLoad: BaseFragment,
                     addToBackStack: Boolean = false, addToBackStackTag: String? = null,
                     animate: Boolean = false)

    class NoContextException : Exception()

}