package com.spaetimc.data.product

import arrow.core.Option
import arrow.core.firstOrNone
import com.spaetimc.data.ProductRepository
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.utils.AppProject
import com.spaetimc.utils.productToAppProduct
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val appProject: AppProject
) : ProductRepository {

    override suspend fun getAppProduct(barCode: String): Option<AppProduct> = appProject
        .products().get().addWhere("masterData(current(masterVariant(sku=\"$barCode\")))")
        .executeBlocking().body.results
        .map { productToAppProduct(it) }
        .firstOrNone()

}