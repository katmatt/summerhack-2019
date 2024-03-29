package com.spaetimc.data.order

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
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val appProject: AppProject
) : OrderRepository {

    override suspend fun makeOrder(customer: Customer, appProducts: List<AppProduct>): Order =
        createCart(customer, appProducts).let {
            appProject.orders().post(it.toOrderDraft()).executeBlocking().body
        }

    private fun createCart(customer: Customer, appProducts: List<AppProduct>): Cart =
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
        }.let { appProject.carts().post(it).executeBlocking().body }

}