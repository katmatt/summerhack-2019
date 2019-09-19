package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe

interface ScanProductUseCase {

    fun getProduct(barcode: String): Maybe<AppProduct>

}