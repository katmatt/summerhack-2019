package com.spaetimc.di

import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.OrderRepository
import com.spaetimc.data.ProductRepository
import com.spaetimc.data.order.OrderRepositoryImpl
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

    fun getAppProject(): AppProject

    fun getProductRepository(): ProductRepository

    fun getCustomerRepository(): CustomerRepository

    fun getOrderRepository(): OrderRepositoryImpl

}
