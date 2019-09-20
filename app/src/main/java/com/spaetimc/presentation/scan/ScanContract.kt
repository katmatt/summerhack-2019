package com.spaetimc.presentation.scan

import com.google.zxing.Result
import com.spaetimc.presentation.BasePresenter
import com.spaetimc.presentation.BaseView
import com.spaetimc.presentation.scan.model.AppProduct

interface ScanContract {

    interface ScanView : BaseView {

        fun initializeProductList()

        fun updateProductList(productList: List<AppProduct>)

        fun updateTotalPrice(centAmount: Int)

        fun initOnClickListeners()

        fun reStartCamera()

        fun showCheckoutScreen(orderNumber: String)

        fun requestPermissions()
    }

    interface ScanPresenter : BasePresenter {

        fun checkout()

        fun cancelOrder()

        fun handleNewBarcode(barcode: Result?)

    }

}