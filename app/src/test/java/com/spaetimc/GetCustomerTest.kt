package com.spaetimc

import com.spaetimc.di.DaggerTestComponent
import org.junit.Test

import org.junit.Assert.*

class GetCustomerTest {

    val testComponent = DaggerTestComponent.create()

    @Test
    fun testMainCustomer() {
        val mainCustomer = testComponent.getGodRepository().getMainCustomer().blockingGet()
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
                .getGodRepository()
                .getAppProduct("")
                .isEmpty
                .blockingGet()
        )
    }

    @Test
    fun getAppProductByBarcode() {
        assertFalse(
            testComponent
                .getGodRepository()
                .getAppProduct("4029764001401")
                .isEmpty
                .blockingGet()
        )
    }
}
