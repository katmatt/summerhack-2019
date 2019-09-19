package com.spaetimc.data

import com.commercetools.models.customer.Customer
import io.reactivex.Maybe
import io.reactivex.Single

interface CustomerRepository {

    fun getMainCustomer(): Single<Customer>

}