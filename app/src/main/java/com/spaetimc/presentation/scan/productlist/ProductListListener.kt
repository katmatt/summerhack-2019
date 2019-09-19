package com.spaetimc.presentation.scan.productlist

import com.spaetimc.presentation.scan.model.AppProduct

interface ProductListListener {

    fun onPlusButtonClicked(product: AppProduct)

    fun onMinusButtonClicked(product: AppProduct)

}