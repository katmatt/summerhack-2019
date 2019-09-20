package com.spaetimc.presentation.checkout

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CheckoutPresenter @Inject constructor(
    private val checkoutView: CheckoutContract.CheckoutView,
    private val compositeDisposable: CompositeDisposable
) : CheckoutContract.CheckoutPresenter {

    override fun start() = checkoutView.initViews()

    override fun stop() = compositeDisposable.dispose()

    override fun handleBackPressed() = checkoutView.doOnBackPressed()

}