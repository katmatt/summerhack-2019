package com.spaetimc.data.product

import com.commercetools.models.cart.Cart
import com.commercetools.models.order.Order
import com.commercetools.models.product.ProductVariant
import com.spaetimc.data.ProductRepository
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.utils.AppProject
import com.spaetimc.utils.productToAppProduct
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val appProject: AppProject
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

    override fun createCart(): Single<Cart> = TODO("not implemented")

    override fun makeOrder(cartId: String): Single<Order> = TODO("not implemented")

}