package com.spaetimc.di

import com.commercetools.client.ApiRoot
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.vrap.rmf.base.client.VrapHttpClient
import io.vrap.rmf.base.client.middlewares.HttpMiddleware
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
    fun getClientId(): String = ""

    @Provides
    @Singleton
    @Named("client_secret")
    fun getClientSecret(): String = ""

    @Provides
    @Singleton
    @Named("project")
    fun getProject(): String = ""

    @Provides
    @Singleton
    @Named("scopes")
    fun getScopes(
        @Named("project_name") project_name:String
    ): String = "manage_project:$project_name"

    @Provides
    @Singleton
    fun getApiRoot(
        vrapHttpClient: VrapHttpClient,
        @Named("client_id") client_id: String,
        @Named("client_secret") client_secret: String,
        @Named("scopes") scopes: String
    ): ApiRoot {
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
        return ApiRoot.fromMiddlewares(httpMiddleware)
    }

}