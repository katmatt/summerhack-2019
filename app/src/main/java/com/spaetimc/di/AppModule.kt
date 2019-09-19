package com.spaetimc.di

import com.commercetools.client.ApiRoot
import com.spaetimc.data.GodRepository
import com.spaetimc.data.GodRepositoryImpl
import com.spaetimc.utils.AppProject
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.vrap.rmf.base.client.VrapHttpClient
import io.vrap.rmf.base.client.middlewares.HttpMiddleware
import io.vrap.rmf.base.client.middlewares.LoggerMiddleware
import io.vrap.rmf.base.client.oauth2.ClientCredentialsTokenSupplier
import io.vrap.rmf.impl.okhttp.VrapOkhttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    fun provideCompositeDisposables() = CompositeDisposable()
    @Provides
    @Singleton
    fun getHttpClient(): VrapHttpClient = VrapOkhttpClient()

    @Provides
    @Singleton
    @Named("client_id")
    fun getClientId(): String = "vlpXbEFrcRN25J_yxKE0n9M6"

    @Provides
    @Singleton
    @Named("client_secret")
    fun getClientSecret(): String = "Z_3BH5aqyAS-LcSKlKcRzXNoSyXkRsv3"

    @Provides
    @Singleton
    @Named("project_key")
    fun getProjectKey(): String = "spati-mc"

    @Provides
    @Singleton
    @Named("scopes")
    fun getScopes(
        @Named("project_key") project_key:String
    ): String = "manage_project:$project_key"

    @Provides
    @Singleton
    fun getApiRoot(
        vrapHttpClient: VrapHttpClient,
        @Named("client_id") client_id: String,
        @Named("client_secret") client_secret: String,
        @Named("project_key") project_key: String,
        @Named("scopes") scopes: String
    ): AppProject {
        val httpMiddleware = HttpMiddleware(
            "https://api.sphere.io",
            vrapHttpClient,
            ClientCredentialsTokenSupplier(
                client_id,
                client_secret,
                scopes,
                "https://auth.sphere.io/oauth/token",
                vrapHttpClient
            )
        )
        return ApiRoot.fromMiddlewares(httpMiddleware).withProjectKey(project_key)
    }



}