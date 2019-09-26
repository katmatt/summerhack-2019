package com.spaetimc.presentation.checkout.di

import com.spaetimc.presentation.checkout.CheckoutActivity
import com.spaetimc.presentation.checkout.CheckoutContract
import com.spaetimc.presentation.checkout.CheckoutPresenter
import dagger.Module
import dagger.Provides

@Module
class CheckoutActivityModule {

    @Provides
    fun provideCheckoutPresenter(checkoutActivity: CheckoutActivity): CheckoutContract.CheckoutPresenter =
        CheckoutPresenter(checkoutActivity)

}