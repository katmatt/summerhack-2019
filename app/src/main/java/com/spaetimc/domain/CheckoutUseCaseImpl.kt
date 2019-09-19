package com.spaetimc.domain

import com.commercetools.models.order.Order
import com.spaetimc.data.ProductRepository
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Single
import javax.inject.Inject

class CheckoutUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : CheckoutUseCase {

    override fun checkout(productList: List<AppProduct>): Single<Order> {
        return productRepository.makeOrder(productList)
    }
}