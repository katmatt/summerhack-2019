package com.spaetimc.data.customer

import com.commercetools.models.customer.Customer
import com.spaetimc.data.CustomerRepository
import com.spaetimc.utils.AppProject
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val appProject: AppProject
) : CustomerRepository {

    override suspend fun getMainCustomer(): Customer = getCustomer()
        ?.let { it }
        ?: appProject.customers().get().addWhere("email = \"spaetimc@mc.com\"")
            .executeBlocking().body.results.first()


    private fun getCustomer(): Customer? = appProject
        .customers().get().addWhere("email = \"spaetimc@mc.com\"")
        .executeBlocking().body.results
        .firstOrNull()

}