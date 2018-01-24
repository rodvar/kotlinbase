package com.rodvar.kotlinbase.base.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.AppView.Companion.NO_VIEW
import com.rodvar.kotlinbase.base.utils.android.DeviceUtils
import com.rodvar.kotlinbase.base.utils.android.Logger
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


/**
 * Created by rodvar on 29/8/17.
 *
 * Sample App Base Activity for all classes
 */
abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, AppView {

    // default is to not change title
    override val title: Int
        get() = NO_VIEW

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    private fun inject() {
        try {
            AndroidInjection.inject(this)
        } catch (e: Exception) {
            Logger.warnError(javaClass, "inject activity", e)
        }
    }

    /**
     * Loads fragment into the main fragment container
     */
    override fun loadFragment(containerId: Int, fragmentToLoad: BaseFragment, addToBackStack: Boolean,
                              addToBackStackTag: String?, animate: Boolean) {
        var transaction = supportFragmentManager.beginTransaction()
                .replace(containerId, fragmentToLoad)
        if (addToBackStack)
            transaction = transaction.addToBackStack(addToBackStackTag)
        if (animate)
            transaction = transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
        this.executeFragmentTransaction(transaction)
    }

    /**
     * @param transaction
     * @param addToBackStack
     */
    private fun executeFragmentTransaction(transaction: FragmentTransaction) {
        try {
            transaction.commit()
        } catch (e: Exception) {
            Logger.error(javaClass.simpleName, "LIFECYCLE Failed to load fragment", e)
        }

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun context(): Context {
        return this
    }

    /**
     * Toast the message to the user. Thread safe.
     *
     * @param message the message to toast
     */
    fun toast(message: String) {
        this.runOnUiThread { Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show() }
    }

    /**
     * Forces portrait for handsets that does not allow landscape (TODO is this needed..?)
     */
    fun forcePortraitForDevice() {
        if (isHandset()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * @param lock if true, locks rotation, unlocks it otherwise
     */
    fun lockRotation(lock: Boolean) {
        if (lock) {
            if (resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT)
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }

    fun isHandset(): Boolean {
        return DeviceUtils.isHandset(this)
    }

    fun sendToBackground() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(homeIntent)
    }


    /**
     * Show app's defaul spinner
     */
    fun showLoading() {
        this.progressBar.visibility = View.VISIBLE
    }

    /**
     * hides app's default spinner
     */
    fun hideLoading() {
        this.progressBar.visibility = View.GONE
    }

}