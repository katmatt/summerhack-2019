package com.spaetimc.data

import arrow.core.Option
import com.spaetimc.presentation.scan.model.AppProduct

interface ProductRepository {

    suspend fun getAppProduct(barCode: String): Option<AppProduct>

}