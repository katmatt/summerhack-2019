package com.spaetimc.presentation.checkout.di

import com.spaetimc.presentation.checkout.CheckoutActivity
import com.spaetimc.presentation.checkout.CheckoutContract
import com.spaetimc.presentation.checkout.CheckoutPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CheckoutActivityModule {

    @Provides
    fun provideCheckoutPresenter(
        checkoutActivity: CheckoutActivity,
        compositeDisposable: CompositeDisposable
    ): CheckoutContract.CheckoutPresenter = CheckoutPresenter(checkoutActivity, compositeDisposable)

}