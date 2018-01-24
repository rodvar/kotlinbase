package com.rodvar.kotlinbase.feature

import android.os.Bundle
import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseActivity
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import javax.inject.Inject

/**
 * Main activity is the principal activity of the app that lives during the whole lifecycle starting
 * after Splash & Login activity.
 * Other activities could be created, but this one is the boss.
 *
 * What is presented to the user is controlled by the mainFragment.
 * Navigation is provided by this activity
 */
class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainFragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(R.id.main_container, mainFragment, true, animate = true)
    }

    override fun onBackPressed() {
        if (!mainFragment.onBackPressed())
            this.finish()
        super.onBackPressed()
    }
}
