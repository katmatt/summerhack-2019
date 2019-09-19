package com.spaetimc.presentation.scan.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.data.ProductRepository
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.domain.checkout.CheckoutUseCaseImpl
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.domain.scan.ScanProductUseCaseImpl
import com.spaetimc.presentation.scan.ScanActivity
import com.spaetimc.presentation.scan.ScanContract
import com.spaetimc.presentation.scan.ScanPresenter
import com.spaetimc.presentation.scan.productlist.ProductListListener
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ScanActivityModule {

    @Provides
    fun provideScanPresenter(
        scanActivity: ScanActivity,
        scanProductUseCase: ScanProductUseCase,
        checkoutUseCase: CheckoutUseCase,
        compositeDisposable: CompositeDisposable
    ): ScanContract.ScanPresenter = ScanPresenter(scanActivity, scanProductUseCase, checkoutUseCase, compositeDisposable)

    @Provides
    fun provideScanView(scanActivity: ScanActivity): ScanContract.ScanView = scanActivity

    @Provides
    fun provideScanProductUseCase(productRepository: ProductRepository): ScanProductUseCase =
        ScanProductUseCaseImpl(productRepository)

    @Provides
    fun provideScanCheckoutUseCase(checkoutUseCaseImpl: CheckoutUseCaseImpl): CheckoutUseCase =
        checkoutUseCaseImpl

    @Provides
    fun provideLinearLayoutManager(scanActivity: ScanActivity) = LinearLayoutManager(scanActivity)

    @Provides
    fun provideProductListAdapterCallback(scanPresenter: ScanPresenter): ProductListListener = scanPresenter

}