package com.spaetimc.presentation.scan

import com.spaetimc.presentation.BasePresenter
import com.spaetimc.presentation.BaseView

interface ScanContract {

    interface ScanView : BaseView {
        fun initializeProductlist()

        fun initScanner()

        fun updateProductList(productList: List<String>)
    }

    interface ScanPresenter : BasePresenter {

    }

}