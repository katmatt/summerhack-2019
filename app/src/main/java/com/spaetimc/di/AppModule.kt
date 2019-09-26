package com.spaetimc.di

import com.commercetools.client.ApiRoot
import com.spaetimc.utils.AppProject
import dagger.Module
import dagger.Provides
import io.vrap.rmf.base.client.VrapHttpClient
import io.vrap.rmf.base.client.middlewares.HttpMiddleware
import io.vrap.rmf.base.client.oauth2.ClientCredentialsTokenSupplier
import io.vrap.rmf.impl.okhttp.VrapOkhttpClient
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    fun provideJob() = Job()

    @Provides
    @Singleton
    fun provideHttpClient(): VrapHttpClient = VrapOkhttpClient()

    @Provides
    @Singleton
    @Named("client_id")
    fun provideClientId(): String = ""

    @Provides
    @Singleton
    @Named("client_secret")
    fun provideClientSecret(): String = ""

    @Provides
    @Singleton
    @Named("project_key")
    fun provideProjectKey(): String = "spati-mc"

    @Provides
    @Singleton
    @Named("scopes")
    fun provideScopes(
        @Named("project_key") project_key: String
    ): String = "manage_project:$project_key"

    @Provides
    @Singleton
    fun provideApiRoot(
        vrapHttpClient: VrapHttpClient,
        @Named("client_id") client_id: String,
        @Named("client_secret") client_secret: String,
        @Named("project_key") project_key: String,
        @Named("scopes") scopes: String
    ): AppProject = HttpMiddleware(
        "https://api.sphere.io",
        vrapHttpClient,
        ClientCredentialsTokenSupplier(
            client_id,
            client_secret,
            scopes,
            "https://auth.sphere.io/oauth/token",
            vrapHttpClient
        )
    ).let { httpMiddleware -> ApiRoot.fromMiddlewares(httpMiddleware).withProjectKey(project_key) }

}