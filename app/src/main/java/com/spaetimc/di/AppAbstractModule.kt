package com.spaetimc.di

import com.spaetimc.data.ProductRepository
import com.spaetimc.data.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class AppAbstractModule {

    @Binds
    abstract fun getGodRpository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

}