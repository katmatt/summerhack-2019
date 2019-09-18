package com.spaetimc.domain

import io.reactivex.Flowable

interface ScanProductUseCase {

    fun scanProduct(): Flowable<String>

}