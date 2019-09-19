package com.spaetimc.data.product

import com.commercetools.models.cart.Cart
import com.commercetools.models.cart.CartDraftImpl
import com.commercetools.models.cart.TaxMode
import com.commercetools.models.common.AddressImpl
import com.commercetools.models.order.Order
import com.commercetools.models.product.ProductVariant
import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.ProductRepository
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.utils.AppProject
import com.spaetimc.utils.productToAppProduct
import com.spaetimc.utils.toLineItemDraft
import com.spaetimc.utils.toOrderDraft
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val appProject: AppProject,
    private val customerRepository: CustomerRepository
) : ProductRepository {

    fun getProduct(): Flowable<ProductVariant> = Flowable
        .fromCallable {
            appProject
                .productProjections()
                .get()
                .executeBlocking()
                .body
        }
        .flatMapIterable { it.results }
        .map { it.masterVariant }

    override fun getAppProduct(barCode: String): Maybe<AppProduct> = Maybe
        .fromCallable {
            appProject
                .products()
                .get()
                .addWhere("masterData(current(masterVariant(sku=\"$barCode\")))")
                .executeBlocking()
                .body
                .results
        }
        .flatMap {
            if (it.isNullOrEmpty()) Maybe.empty()
            else Maybe.just(productToAppProduct(it[0]))
        }

    override fun createCart(appProducts: List<AppProduct>): Single<Cart> = customerRepository
        .getMainCustomer()
        .map {
            val cartDraftImpl = CartDraftImpl()
            cartDraftImpl.currency = "EUR"
            cartDraftImpl.customerId = it.id
            cartDraftImpl.lineItems = appProducts.map { it.toLineItemDraft() }
            cartDraftImpl.taxMode = TaxMode.DISABLED
            val address = AddressImpl()
            address.city = "BERLIN"
            address.country = "DE"
            cartDraftImpl.shippingAddress = address
            cartDraftImpl
        }
        .map {
            appProject
                .carts()
                .post(it)
                .executeBlocking()
                .body
        }

    override fun makeOrder(appProducts: List<AppProduct>): Single<Order> = createCart(appProducts)
        .map {
            appProject
                .orders()
                .post(
                    it.toOrderDraft()
                )
                .executeBlocking()
                .body
        }

}