package com.rodvar.kotlinbase.feature

import android.content.Intent
import android.os.Bundle
import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseActivity
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import javax.inject.Inject

/**
 * The starting point of the app (launch activity).
 * Should lunch configurators to initialize in background and die.
 *
 * Only remain alive showing loading if needs to wait for some config to be loaded prior to login
 */
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var mainFragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}