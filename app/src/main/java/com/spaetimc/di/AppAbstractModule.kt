package com.spaetimc.di

import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.OrderRepository
import com.spaetimc.data.ProductRepository
import com.spaetimc.data.customer.CustomerRepositoryImpl
import com.spaetimc.data.order.OrderRepositoryImpl
import com.spaetimc.data.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class AppAbstractModule {

    @Binds
    @Singleton
    abstract fun provideProductRpository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository


    @Binds
    @Singleton
    abstract fun provideCustomerRpository(customerRepositoryImpl: CustomerRepositoryImpl): CustomerRepository

    @Binds
    @Singleton
    abstract fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

}