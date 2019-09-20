package com.spaetimc.data.order

import androidx.annotation.VisibleForTesting
import com.commercetools.models.cart.Cart
import com.commercetools.models.cart.CartDraftImpl
import com.commercetools.models.cart.TaxMode
import com.commercetools.models.common.AddressImpl
import com.commercetools.models.customer.Customer
import com.commercetools.models.order.Order
import com.spaetimc.data.OrderRepository
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.utils.AppProject
import com.spaetimc.utils.toLineItemDraft
import com.spaetimc.utils.toOrderDraft
import io.reactivex.Single
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val appProject: AppProject
) : OrderRepository {

    override fun makeOrder(customer: Customer, appProducts: List<AppProduct>): Single<Order> =
        createCart(customer, appProducts)
            .map {
                appProject
                    .orders()
                    .post(it.toOrderDraft())
                    .executeBlocking()
                    .body
            }
    @VisibleForTesting
    fun createCart(customer: Customer, appProducts: List<AppProduct>): Single<Cart> = Single
        .just(
            AddressImpl().also { address ->
                address.city = "BERLIN"
                address.country = "DE"
            }.let { address ->
                CartDraftImpl().also { cartDraftImpl ->
                    cartDraftImpl.currency = "EUR"
                    cartDraftImpl.customerId = customer.id
                    cartDraftImpl.lineItems = appProducts.map { it.toLineItemDraft() }
                    cartDraftImpl.taxMode = TaxMode.DISABLED
                    cartDraftImpl.shippingAddress = address
                }
            }
        )
        .map {
            appProject
                .carts()
                .post(it)
                .executeBlocking()
                .body
        }

}