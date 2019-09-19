package com.spaetimc.domain

import com.commercetools.models.order.Order
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Single

interface CheckoutUseCase {

    fun checkout(productList: List<AppProduct>): Single<Order>

}