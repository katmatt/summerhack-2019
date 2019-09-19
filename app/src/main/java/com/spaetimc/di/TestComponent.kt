package com.spaetimc.di

import android.app.Application
import com.commercetools.models.customer.Customer
import com.spaetimc.MyApplication
import com.spaetimc.data.GodRepository
import com.spaetimc.utils.AppProject
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.reactivex.Single
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
