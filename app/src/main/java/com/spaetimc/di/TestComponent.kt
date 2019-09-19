package com.spaetimc.di

import com.spaetimc.data.GodRepository
import com.spaetimc.utils.AppProject
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppAbstractModule::class
    ]
)

interface TestComponent {

    fun getGodRepository(): GodRepository

    fun getAppProject(): AppProject

}

fun main() {
    println(
        DaggerTestComponent
            .create()
            .getGodRepository()
            .getMainCustomer()
            .blockingGet()
    )
}