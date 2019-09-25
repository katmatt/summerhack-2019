package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct

interface ScanProductUseCase {

    suspend fun getProduct(barcode: String): AppProduct?

}