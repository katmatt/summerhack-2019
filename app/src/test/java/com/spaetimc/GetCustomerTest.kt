package com.spaetimc

import com.spaetimc.di.DaggerTestComponent
import com.spaetimc.presentation.scan.model.AppProduct
import org.junit.Test

import org.junit.Assert.*

class GetCustomerTest {

    val testComponent = DaggerTestComponent.create()

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
    fun setTaxCategory(){
        assertFalse(testComponent
            .getProductRepository()
            .getAppProduct("4029764001401")
            .isEmpty
            .blockingGet()
        )
    }



    @Test
    fun testCreateCart(){
        testComponent.getProductRepository()
            .getAppProduct("4029764001401")
            .map {
                listOf(it)
            }
            .toSingle()
            .flatMap {
                testComponent
                    .getProductRepository()
                    .createCart(it)
            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue{
                it.id != null
            }



    }


    @Test
    fun testMakeOrder(){
        testComponent.getProductRepository()
            .getAppProduct("4029764001401")
            .map {
                listOf(it)
            }
            .toSingle()
            .flatMap {
                testComponent
                    .getProductRepository()
                    .makeOrder(it)
            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.id != null
            }
    }
}
