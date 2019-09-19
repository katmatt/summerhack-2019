package com.spaetimc.di

import android.app.Application
import com.commercetools.models.customer.Customer
import com.spaetimc.MyApplication
import com.spaetimc.data.GodRepository
import com.spaetimc.data.GodRepositoryImpl
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AppAbstractModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)

}