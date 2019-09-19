package com.spaetimc.di

import com.spaetimc.data.CustomerRepository
import com.spaetimc.data.ProductRepository
import com.spaetimc.data.customer.CustomerRepositoryImpl
import com.spaetimc.data.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class AppAbstractModule {

    @Binds
    abstract fun getProductRpository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository


    @Binds
    abstract fun getCustomerRpository(productRepositoryImpl: CustomerRepositoryImpl): CustomerRepository


}