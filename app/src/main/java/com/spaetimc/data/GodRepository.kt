package com.spaetimc.data

import com.commercetools.models.cart.Cart
import com.commercetools.models.customer.Customer
import com.commercetools.models.order.Order
import com.commercetools.models.product.ProductVariant
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Maybe
import io.reactivex.Single

interface GodRepository {

    fun createCustomer(): Single<Customer>

    fun getCustomer(): Maybe<Customer>

    fun getMainCustomer(): Single<Customer>

    fun getAppProduct(barCode: String): Maybe<AppProduct>

    fun createCart(): Single<Cart>

    fun makeOrder(cartId: String): Single<Order>

}