package com.spaetimc

import com.spaetimc.di.DaggerTestComponent
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val testComponent = DaggerTestComponent.create()

    @Test
    fun testMainCustomer(){
        val mainCustomer = testComponent.getGodRepository().getMainCustomer().blockingGet()
        println(mainCustomer.email)
        assertNotNull(mainCustomer)
    }
}
