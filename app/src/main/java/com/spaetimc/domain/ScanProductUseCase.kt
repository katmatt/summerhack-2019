package com.spaetimc.domain

import arrow.core.Option
import com.spaetimc.presentation.scan.model.AppProduct

interface ScanProductUseCase {

    suspend fun getProduct(barcode: String): Option<AppProduct>

}