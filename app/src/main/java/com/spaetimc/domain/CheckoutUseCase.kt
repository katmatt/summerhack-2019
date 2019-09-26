package com.spaetimc.domain

import com.commercetools.models.order.Order
import com.spaetimc.presentation.scan.model.AppProduct

interface CheckoutUseCase {

    suspend fun checkout(productList: List<AppProduct>): Order

}