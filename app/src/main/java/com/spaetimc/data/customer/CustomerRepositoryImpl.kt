package com.spaetimc.data.customer

import com.commercetools.models.customer.Customer
import com.commercetools.models.customer.CustomerDraftImpl
import com.spaetimc.data.CustomerRepository
import com.spaetimc.utils.AppProject
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val appProject: AppProject
) : CustomerRepository {

    override fun createCustomer(): Single<Customer> = CustomerDraftImpl()
        .also { customerDraftImpl ->
            customerDraftImpl.email = "spaetimc@mc.com"
            customerDraftImpl.password = "spaetimc@mc.com"
            customerDraftImpl.firstName = "Mc"
            customerDraftImpl.lastName = "Greg"
        }
        .let { customerDraftImpl ->
            Single.fromCallable {
                appProject
                    .customers()
                    .post(customerDraftImpl)
                    .executeBlocking()
                    .body
                    .customer
            }
        }

    override fun getCustomer(): Maybe<Customer> = Maybe
        .fromCallable {
            appProject
                .customers()
                .get()
                .addWhere("email = \"spaetimc@mc.com\"")
                .executeBlocking()
                .body
                .results
        }
        .flatMap {
            if (it.isNullOrEmpty()) Maybe.empty()
            else Maybe.just(it[0])
        }

    override fun getMainCustomer(): Single<Customer> = getCustomer().switchIfEmpty(createCustomer())

}