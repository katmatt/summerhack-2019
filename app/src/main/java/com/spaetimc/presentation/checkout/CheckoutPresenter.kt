package com.spaetimc.presentation.checkout

import javax.inject.Inject

class CheckoutPresenter @Inject constructor(
    private val checkoutView: CheckoutContract.CheckoutView
) : CheckoutContract.CheckoutPresenter {

    override fun start() = checkoutView.initViews()

    override fun stop() = Unit

    override fun handleBackPressed() = checkoutView.doOnBackPressed()

}