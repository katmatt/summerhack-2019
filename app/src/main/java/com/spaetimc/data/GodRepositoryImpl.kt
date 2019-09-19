package com.spaetimc.data

import com.commercetools.models.cart.Cart
import com.commercetools.models.customer.Customer
import com.commercetools.models.customer.CustomerDraftImpl
import com.commercetools.models.order.Order
import com.commercetools.models.product.ProductVariant
import com.spaetimc.utils.AppProject
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class GodRepositoryImpl @Inject constructor(val appProject: AppProject) : GodRepository {

    override fun createCustomer(): Single<Customer> {

        val customerDraftImpl = CustomerDraftImpl();
        customerDraftImpl.email = "spaetimc@mc.com"
        customerDraftImpl.password = "spaetimc@mc.com"

        return Single.fromCallable {
            appProject
                .customers()
                .post(customerDraftImpl)
                .executeBlocking()
                .body
                .customer
        }

    }

    override fun getCustomer(): Maybe<Customer> {
        return Maybe.fromCallable {
            appProject
                .customers()
                .get()
                .addWhere("email = \"spaetimc@mc.com\"")
                .executeBlocking()
                .body
                .results
        }
            .flatMap {
                if (it.isNullOrEmpty()) {
                    Maybe.empty()
                } else {
                    Maybe.just(it[0])
                }
            }

    }

    override fun getMainCustomer(): Single<Customer> = getCustomer()
        .switchIfEmpty(
            createCustomer()
        )

    override fun getProductVariant(barCode: String): Single<ProductVariant> = TODO("not implemented")

    override fun createCart(): Single<Cart> = TODO("not implemented")

    override fun makeOrder(cartId: String): Single<Order> = TODO("not implemented")

}