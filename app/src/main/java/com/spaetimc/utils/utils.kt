package com.spaetimc.utils

import com.commercetools.client.ByProjectKeyRequestBuilder
import com.commercetools.models.product.Product
import com.spaetimc.presentation.scan.model.AppProduct

typealias AppProject = ByProjectKeyRequestBuilder


fun productToAppProduct(product: Product): AppProduct {
    return AppProduct(
        name = product.masterData.current.name.values()["de-DE"] ?: "NO name found",
        description = product.masterData.current.description.values()["de-DE"] ?: "NO description found",
        pictureUrl = product.masterData.current.masterVariant.images[0]?.url ?: "default_url",
        price = product.priceAsString(),
        barcodeValue = product.masterData.current.masterVariant.sku
    )
}

fun Product.priceAsString(): String {
    if(this.masterData.current.masterVariant.prices[0]==null){
        return "Price not available"
    }
    return "${this.masterData.current.masterVariant.prices[0].value.centAmount / 100} ${this.masterData.current.masterVariant.prices[0].value.currencyCode}"
}