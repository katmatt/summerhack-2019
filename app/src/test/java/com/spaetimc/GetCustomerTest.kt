package com.spaetimc

import com.commercetools.models.customer.Customer
import com.spaetimc.di.DaggerTestComponent
import com.spaetimc.di.TestComponent
import org.junit.Assert.*
import org.junit.Test

class GetCustomerTest {

    private val testComponent: TestComponent = DaggerTestComponent.create()

    @Test
    fun testMainCustomer() {
        val mainCustomer = testComponent
            .getCustomerRepository()
            .getMainCustomer().blockingGet()
        println(mainCustomer.email)
        assertNotNull(mainCustomer)
    }

    @Test
    fun getAllSkus() {
        println(
            testComponent
                .getAppProject()
                .productProjections()
                .get()
                .executeBlocking()
                .body
                .results
                .map { it.masterVariant.sku }
        )
    }

    @Test
    fun searchBySky() {
        println(
            testComponent
                .getAppProject()
                .products()
                .get()
                .addWhere("masterData(current(masterVariant(sku=\"4012852001209\")))")
                .executeBlocking()
                .body
                .results
        )
    }


    @Test
    fun getNonExistingAppProductByBarcode() {
        assertTrue(
            testComponent
                .getProductRepository()
                .getAppProduct("")
                .isEmpty
                .blockingGet()
        )
    }

    @Test
    fun getAppProductByBarcode() {
        assertFalse(
            testComponent
                .getProductRepository()
                .getAppProduct("4029764001401")
                .isEmpty
                .blockingGet()
        )
    }

    @Test
    fun setTaxCategory() {
        assertFalse(
            testComponent
                .getProductRepository()
                .getAppProduct("4029764001401")
                .isEmpty
                .blockingGet()
        )
    }


    @Test
    fun testCreateCart() {
        testComponent
            .getCustomerRepository()
            .getMainCustomer()
            .flatMap { t: Customer ->
                testComponent.getProductRepository()
                    .getAppProduct("4029764001401")
                    .map {
                        listOf(it)
                    }
                    .toSingle()
                    .flatMap {
                        testComponent
                            .getOrderRepository()
                            .createCart(t, it)
                    }

            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.id != null
            }

    }


    @Test
    fun testMakeOrder() {
        testComponent
            .getCustomerRepository()
            .getMainCustomer()
            .flatMap { t: Customer ->
                testComponent.getProductRepository()
                    .getAppProduct("4029764001401")
                    .map {
                        listOf(it)
                    }
                    .toSingle()
                    .flatMap {
                        testComponent
                            .getOrderRepository()
                            .makeOrder(t, it)
                    }

            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.id != null
            }
    }
}
