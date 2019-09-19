package com.spaetimc.domain

import com.spaetimc.data.ProductRepository
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ScanProductUseCase {

    override fun getProduct(barcode: String): Maybe<AppProduct> = productRepository.getAppProduct(barcode)

}