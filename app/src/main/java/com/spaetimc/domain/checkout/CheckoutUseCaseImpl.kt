package com.spaetimc.domain.checkout

import com.commercetools.models.order.Order
import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.OrderRepository
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Single
import javax.inject.Inject

class CheckoutUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) : CheckoutUseCase {

    override fun checkout(productList: List<AppProduct>): Single<Order> = customerRepository
        .getMainCustomer()
        .flatMap { customer -> orderRepository.makeOrder(customer, productList) }

}