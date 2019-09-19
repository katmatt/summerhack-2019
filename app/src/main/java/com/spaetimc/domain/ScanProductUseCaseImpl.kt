package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Flowable
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor() : ScanProductUseCase {

    override fun scanProduct(): Flowable<AppProduct> = Flowable.just(
        AppProduct(name = "Club Mate", description = "0,5ml", price = "0,99", pictureUrl = "", amount = 1,barcodeValue = ""),
        AppProduct(name = "Mio Mate", description = "0,5ml", price = "0,99", pictureUrl = "", amount = 1,barcodeValue = "")
    )

}