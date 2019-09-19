package com.spaetimc.presentation.scan

import com.spaetimc.presentation.BasePresenter
import com.spaetimc.presentation.BaseView
import com.spaetimc.presentation.scan.model.AppProduct

interface ScanContract {

    interface ScanView : BaseView {

        fun initializeProductList()

        fun initScanner()

        fun updateProductList(productList: List<AppProduct>)

        fun initOnClickListners()

    }

    interface ScanPresenter : BasePresenter {

        fun checkout()

        fun cancelOrder()

    }

}