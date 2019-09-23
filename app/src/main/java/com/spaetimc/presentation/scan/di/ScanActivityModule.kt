package com.spaetimc.presentation.scan.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.data.ProductRepository
import com.spaetimc.di.ActivityScope
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.domain.checkout.CheckoutUseCaseImpl
import com.spaetimc.domain.scan.ScanProductUseCaseImpl
import com.spaetimc.presentation.scan.ScanActivity
import com.spaetimc.presentation.scan.ScanContract
import com.spaetimc.presentation.scan.ScanPresenter
import com.spaetimc.presentation.scan.productlist.ProductListAdapterCallback
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module

class ScanActivityModule {

    @Provides
    @ActivityScope
    fun provideScanPresenter(
        scanActivity: ScanActivity,
        scanProductUseCase: ScanProductUseCase,
        checkoutUseCase: CheckoutUseCase,
        compositeDisposable: CompositeDisposable
    ): ScanPresenter = ScanPresenter(scanActivity, scanProductUseCase, checkoutUseCase, compositeDisposable)

    @Provides
    @ActivityScope
    fun provideScanContractPresenter(scannerPresenter: ScanPresenter): ScanContract.ScanPresenter = scannerPresenter

    @Provides
    @ActivityScope
    fun provideScanView(scanActivity: ScanActivity): ScanContract.ScanView = scanActivity

    @Provides
    @ActivityScope
    fun provideScanProductUseCase(productRepository: ProductRepository): ScanProductUseCase =
        ScanProductUseCaseImpl(productRepository)

    @Provides
    @ActivityScope
    fun provideScanCheckoutUseCase(checkoutUseCaseImpl: CheckoutUseCaseImpl): CheckoutUseCase =
        checkoutUseCaseImpl

    @Provides
    @ActivityScope
    fun provideLinearLayoutManager(scanActivity: ScanActivity) = LinearLayoutManager(scanActivity)

    @Provides
    @ActivityScope
    fun provideProductListAdapterCallback(scanPresenter: ScanPresenter): ProductListAdapterCallback =
        ProductListAdapterCallback(
            onPlusButtonClicked = scanPresenter::onPlusButtonClicked,
            onMinusButtonClicked = scanPresenter::onMinusButtonClicked
        )

}