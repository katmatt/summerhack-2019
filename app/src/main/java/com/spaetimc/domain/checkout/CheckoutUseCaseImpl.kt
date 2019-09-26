package com.spaetimc.domain.checkout

import com.commercetools.models.order.Order
import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.OrderRepository
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import javax.inject.Inject

class CheckoutUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) : CheckoutUseCase {

    override suspend fun checkout(productList: List<AppProduct>): Order =
        customerRepository.getMainCustomer().let { orderRepository.makeOrder(it, productList) }

}