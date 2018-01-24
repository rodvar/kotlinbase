package com.rodvar.kotlinbase.base.presentation

/**
 * Created by rodvar on 5/9/17.
 *
 * Behaviour for any presenter in the app
 */
interface AppPresenter {

    /**
     * binds the view with this presenter
     */
    fun bind(view: AppView)

    /**
     * Determines what to do with the view once created. Generally configures views programmatically
     */
    fun onViewCreated()

    /**
     * on view resumed callback
     */
    fun onResume()

    /**
     * @return true if handled, false otherwise
     */
    fun onBackPressed(): Boolean = false
}