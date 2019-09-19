package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe
import io.reactivex.Single

interface ScanProductUseCase {

    fun getProduct(barcode: String): Maybe<AppProduct>

}