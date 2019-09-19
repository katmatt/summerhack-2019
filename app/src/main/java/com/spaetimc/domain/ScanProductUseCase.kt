package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Flowable

interface ScanProductUseCase {

    fun scanProduct(): Flowable<AppProduct>

}