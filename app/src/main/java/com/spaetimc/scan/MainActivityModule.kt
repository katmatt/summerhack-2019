package com.spaetimc.scan

import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.domain.ScanProductUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideProductListAdapterCallback(mainActivity: MainActivity): ProductListAdapter.ProductListCallback =
        mainActivity

    @Provides
    fun providesScanProductUsecase(scanProductUseCaseImpl: ScanProductUseCaseImpl): ScanProductUseCase = scanProductUseCaseImpl

    @Provides
    fun provideLinearLayoutManager(mainActivity: MainActivity) =
        LinearLayoutManager(mainActivity)

}