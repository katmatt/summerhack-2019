package com.spaetimc.di

import com.spaetimc.data.ProductRepository
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

    fun getGodRepository(): ProductRepository

    fun getAppProject(): AppProject

}
