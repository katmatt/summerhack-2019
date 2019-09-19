package com.spaetimc.presentation.scan

import com.google.zxing.Result
import com.spaetimc.presentation.BasePresenter
import com.spaetimc.presentation.BaseView
import com.spaetimc.presentation.scan.model.AppProduct

interface ScanContract {

    interface ScanView : BaseView {

        fun initializeProductList()

        fun initScanner()

        fun updateProductList(productList: List<AppProduct>)

        fun initOnClickListners()

        fun reStartCamera()

    }

    interface ScanPresenter : BasePresenter {

        fun checkout()

        fun cancelOrder()

        fun handleNewBarcode(barcode: Result?)

    }

}