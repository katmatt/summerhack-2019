package com.spaetimc.data

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe

interface ProductRepository {

    fun getAppProduct(barCode: String): Maybe<AppProduct>

}