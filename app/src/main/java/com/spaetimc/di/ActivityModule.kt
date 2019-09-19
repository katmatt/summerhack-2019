package com.spaetimc.di

import com.spaetimc.presentation.checkout.CheckoutActivity
import com.spaetimc.presentation.checkout.di.CheckoutActivityModule
import com.spaetimc.presentation.scan.ScanActivity
import com.spaetimc.presentation.scan.di.ScanActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ScanActivityModule::class])
    abstract fun contributeScanActivity(): ScanActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [CheckoutActivityModule::class])
    abstract fun contributeCheckoutActivity(): CheckoutActivity

}