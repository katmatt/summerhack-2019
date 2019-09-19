package com.spaetimc.utils

import com.commercetools.client.ByProjectKeyRequestBuilder
import com.commercetools.models.cart.Cart
import com.commercetools.models.cart.LineItemDraft
import com.commercetools.models.cart.LineItemDraftImpl
import com.commercetools.models.order.OrderFromCartDraft
import com.commercetools.models.order.OrderFromCartDraftImpl
import com.commercetools.models.product.Product
import com.spaetimc.presentation.scan.model.AppProduct

typealias AppProject = ByProjectKeyRequestBuilder


fun productToAppProduct(product: Product): AppProduct {
    return AppProduct(
        name = product.masterData.current.name.values()["de-DE"] ?: "NO name found",
        description = product.masterData.current.description.values()["de-DE"] ?: "NO description found",
        pictureUrl = product.masterData.current.masterVariant.images[0]?.url ?: "default_url",
        priceInCent = product.masterData.current.masterVariant.price.value.centAmount.toInt(),
        barcodeValue = product.masterData.current.masterVariant.sku,
        createdAt = System.currentTimeMillis()
    )
}

fun Int.format(): String {
    val eur: Int = this / 100
    val cent: Int = this % 100
    return String.format("%d,%02d", eur, cent)
}

fun Product.priceAsString(): String {
    if(this.masterData.current.masterVariant.prices[0]==null){
        return "Price not available"
    }
    return "${this.masterData.current.masterVariant.prices[0].value.centAmount / 100} ${this.masterData.current.masterVariant.prices[0].value.currencyCode}"
}

fun AppProduct.toLineItemDraft(): LineItemDraft {
    val lineItemDraft = LineItemDraftImpl()
    lineItemDraft.sku = this.barcodeValue
    lineItemDraft.quantity = this.amount.toLong()
    return lineItemDraft
}

fun Cart.toOrderDraft(): OrderFromCartDraft {
    val orderDraft = OrderFromCartDraftImpl()
    orderDraft.id = this.id
    orderDraft.version = this.version
    orderDraft.orderNumber = this.id
    return orderDraft
}