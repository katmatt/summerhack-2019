package com.spaetimc.data

import com.commercetools.models.cart.Cart
import com.commercetools.models.customer.Customer
import com.commercetools.models.order.Order
import com.commercetools.models.product.ProductVariant
import io.reactivex.Single
import javax.inject.Inject

class GodRepositoryImpl @Inject constructor() : GodRepository {

    override fun getMainCustomer(): Single<Customer> = TODO("not implemented")

    override fun getProductVariant(barCode: String): Single<ProductVariant> = TODO("not implemented")

    override fun createCart(): Single<Cart> = TODO("not implemented")

    override fun makeOrder(cartId: String): Single<Order> = TODO("not implemented")

}