package com.spaetimc.domain

import com.spaetimc.data.GodRepository
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor(
    private val godRepository: GodRepository
) : ScanProductUseCase {

    override fun getProduct(barcode: String): Maybe<AppProduct> = godRepository.getAppProduct(barcode)

}