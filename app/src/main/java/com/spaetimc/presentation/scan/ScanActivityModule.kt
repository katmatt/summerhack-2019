package com.spaetimc.presentation.scan

import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.domain.ScanProductUseCaseImpl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ScanActivityModule {

    @Provides
    fun providesScanProductUsecase(scanProductUseCaseImpl: ScanProductUseCaseImpl): ScanProductUseCase =
        scanProductUseCaseImpl

    @Provides
    fun provideLinearLayoutManager(scanActivity: ScanActivity) = LinearLayoutManager(scanActivity)

    @Provides
    fun provideScanPresenter(
        scanActivity: ScanActivity,
        scanProductUseCase: ScanProductUseCase,
        compositeDisposable: CompositeDisposable
    ): ScanContract.ScanPresenter = ScanPresenter(scanActivity, scanProductUseCase, compositeDisposable)

    @Provides
    fun provideScanView(scanActivity: ScanActivity): ScanContract.ScanView = scanActivity

    @Provides
    fun provideProductListAdapterCallback(scanPresenter: ScanPresenter): ProductListListener = scanPresenter

}