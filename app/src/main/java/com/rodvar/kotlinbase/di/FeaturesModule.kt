package com.rodvar.kotlinbase.di

import com.rodvar.kotlinbase.di.scopes.PerFeature
import com.rodvar.kotlinbase.feature.MainActivity
import com.rodvar.kotlinbase.feature.accounts.AccountsMenuFragment
import com.rodvar.kotlinbase.feature.accounts.AccountsModule
import com.rodvar.kotlinbase.feature.accounts.MoreOptionsMenuModule
import com.rodvar.kotlinbase.feature.accounts.TransferMoneyMenuModule
import com.rodvar.kotlinbase.feature.main.MainFragment
import com.rodvar.kotlinbase.feature.main.MainModule
import com.rodvar.kotlinbase.feature.money.TransferMoneyMenuFragment
import com.rodvar.kotlinbase.feature.more.MoreOptionsMenuFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * IMPORTANT!
 *
 * ALL THE FRAGMENT/ACTIVITIES OF ANY FEATURE NEEDS TO HAVE THEIR PROPER @ContributesAndroidInjector
 * method in this class.
 *
 * Otherwise you 'll get NoInjectorFactoryBoundToClass exception
 *
 * Because of the way this has been implemented, here you could reuse presenters and/or fragments,
 * simple pointing your new feature to your existing module on the @ContributesInjector declaration
 */
@Module
abstract class FeaturesModule {

    @PerFeature
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun bindMainActivity(): MainActivity

    @PerFeature
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun bindMainFragment(): MainFragment

    @PerFeature
    @ContributesAndroidInjector(modules = arrayOf(AccountsModule::class))
    abstract fun bindAccountsMenuFragment(): AccountsMenuFragment

    @PerFeature
    @ContributesAndroidInjector(modules = arrayOf(TransferMoneyMenuModule::class))
    abstract fun bindTransferMoneyMenuFragment(): TransferMoneyMenuFragment

    @PerFeature
    @ContributesAndroidInjector(modules = arrayOf(MoreOptionsMenuModule::class))
    abstract fun bindMoreOptionsMenuFragment(): MoreOptionsMenuFragment

}