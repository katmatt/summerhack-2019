package com.spaetimc.di

import com.spaetimc.scan.MainActivity
import com.spaetimc.scan.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeSearchActivity(): MainActivity


}