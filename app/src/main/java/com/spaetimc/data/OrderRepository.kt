package com.spaetimc.data

import com.commercetools.models.customer.Customer
import com.commercetools.models.order.Order
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Single

interface OrderRepository {

    fun makeOrder(customer: Customer, appProducts: List<AppProduct>): Single<Order>

}