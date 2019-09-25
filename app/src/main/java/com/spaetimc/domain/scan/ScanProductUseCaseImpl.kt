package com.spaetimc.domain.scan

import com.spaetimc.data.ProductRepository
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ScanProductUseCase {

    override suspend fun getProduct(barcode: String): AppProduct? = productRepository.getAppProduct(barcode)

}