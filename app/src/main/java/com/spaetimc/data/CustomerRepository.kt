package com.spaetimc.data

import com.commercetools.models.customer.Customer

interface CustomerRepository {

    suspend fun getMainCustomer(): Customer

}