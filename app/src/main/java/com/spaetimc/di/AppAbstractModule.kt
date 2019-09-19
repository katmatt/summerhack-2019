package com.spaetimc.di

import com.spaetimc.data.GodRepository
import com.spaetimc.data.GodRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class AppAbstractModule {

    @Binds
    abstract fun getGodRpository(godRepositoryImpl: GodRepositoryImpl): GodRepository

}