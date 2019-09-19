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

}
