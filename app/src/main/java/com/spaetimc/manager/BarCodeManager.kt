package com.spaetimc.manager

import io.reactivex.Flowable

interface BarCodeManager {

    fun getScanFlow(): Flowable<String>

}