package com.spaetimc.presentation.scan.productlist

import com.spaetimc.presentation.scan.model.AppProduct

data class ProductListAdapterCallback(
    val onPlusButtonClicked: (AppProduct) -> Unit,
    val onMinusButtonClicked: (AppProduct) -> Unit
)