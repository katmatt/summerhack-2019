package com.spaetimc.domain

import io.reactivex.Flowable
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor() : ScanProductUseCase {

    override fun scanProduct(): Flowable<String> = Flowable.just("Product 1", "Product 2")

}