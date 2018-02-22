package com.rodvar.kotlinbase.base.presentation

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.AppView.Companion.NO_VIEW
import com.rodvar.kotlinbase.base.utils.android.Logger

/**
 * Created by rodvar on 5/9/17.
 */
abstract class BasePresenter : AppPresenter {

    lateinit var view: AppView

    override fun onResume() {
        this.changeTitle()
    }

    private fun changeTitle() {
        try {
            if (view.title != NO_VIEW)
                (view as BaseFragment).activity?.setTitle(view.title)
        } catch (e: Exception) {
            Logger.error(javaClass.simpleName, "Failed to change title", e)
        }
    }

    override fun bind(view: AppView) {
        this.view = view
    }

    /**
     * Loads the fragment into the containerId view
     *
     * @param fragmentToLoad
     * @param addToBackStack
     */
    protected fun loadFragment(containerId: Int = R.id.fragment_container, fragmentToLoad: BaseFragment,
                               addToBackStack: Boolean = false, addToBackStackTag: String? = null,
                               animate: Boolean = false) {
        this.view.loadFragment(containerId, fragmentToLoad, addToBackStack, addToBackStackTag,
                animate)
    }
}