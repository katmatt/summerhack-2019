package com.spaetimc.domain

import io.reactivex.Flowable
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor() : ScanProductUseCase {

    override fun scanProduct(): Flowable<String> = TODO(reason = "not implemented")

}