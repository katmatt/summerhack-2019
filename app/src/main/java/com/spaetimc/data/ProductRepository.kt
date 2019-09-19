package com.spaetimc.data

import com.commercetools.models.cart.Cart
import com.commercetools.models.order.Order
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe
import io.reactivex.Single

interface ProductRepository {

    fun getAppProduct(barCode: String): Maybe<AppProduct>

    fun createCart(appProducts: List<AppProduct>): Single<Cart>

    fun makeOrder(appProducts: List<AppProduct>): Single<Order>

}