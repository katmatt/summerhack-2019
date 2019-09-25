package com.spaetimc.data

import com.spaetimc.presentation.scan.model.AppProduct

interface ProductRepository {

    suspend fun getAppProduct(barCode: String): AppProduct?

}