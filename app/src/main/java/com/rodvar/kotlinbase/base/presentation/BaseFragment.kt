package com.rodvar.kotlinbase.base.presentation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodvar.kotlinbase.base.utils.android.Logger
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by rodvar on 29/8/17.
 *
 * Base class for every fragment in the app
 */
abstract class BaseFragment : Fragment(), AppView {

    // default is to not change title
    override val title: Int
        get() = AppView.NO_VIEW

    @Inject
    lateinit var presenter: AppPresenter

    /**
     * @return The resource id to load
     */
    abstract fun getLayoutResId(): Int

    /**
     *
     */
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                           savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutResId(), container, false)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    /**
     * Perform dependency injection, binds injected presenter
     */
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        presenter.bind(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    /**
     * Default implementation
     * @return true if handled, false otherwise
     */
    open fun onBackPressed(): Boolean = presenter.onBackPressed()

    override fun context(): Context {
        when (this.isActivityReady()) {
            true -> return activity as Context
            else -> throw AppView.NoContextException()
        }
    }

    /**
     *
     */
    fun toast(text: String) {
        if (this.isActivityReady())
            (this.activity as BaseActivity).toast(text)
        else
            Logger.debug(javaClass.simpleName, "Cannot toast, activity unready")
    }

    /**
     * Loads the fragment into the containerId view
     *
     * @param fragmentToLoad
     * @param addToBackStack
     */
    override fun loadFragment(containerId: Int, fragmentToLoad: BaseFragment,
                              addToBackStack: Boolean, addToBackStackTag: String?,
                              animate: Boolean) {
        if (this.isActivityReady())
            (this.activity as BaseActivity).loadFragment(containerId, fragmentToLoad,
                    addToBackStack, addToBackStackTag, animate)
    }

    private fun isActivityReady(): Boolean {
        return this.activity != null && !this.activity!!.isFinishing
    }
}