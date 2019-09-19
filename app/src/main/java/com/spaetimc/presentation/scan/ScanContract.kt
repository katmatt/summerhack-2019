package com.spaetimc.presentation.scan

import com.commercetools.models.product.ProductVariant
import com.spaetimc.presentation.BasePresenter
import com.spaetimc.presentation.BaseView
import com.spaetimc.presentation.scan.model.AppProduct

interface ScanContract {

    interface ScanView : BaseView {
        fun initializeProductlist()

        fun initScanner()

        fun updateProductList(productList: List<AppProduct>)
    }

    interface ScanPresenter : BasePresenter {

    }

}