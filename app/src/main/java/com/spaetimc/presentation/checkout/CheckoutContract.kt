package com.spaetimc.presentation.checkout

import com.spaetimc.presentation.BasePresenter

interface CheckoutContract {

    interface CheckoutView {

        fun doOnBackPressed()

        fun initViews()

    }

    interface CheckoutPresenter : BasePresenter {

        fun handleBackPressed()

    }

}