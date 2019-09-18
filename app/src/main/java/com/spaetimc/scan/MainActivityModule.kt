package com.spaetimc.scan

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideProductListAdapterCallback(mainActivity: MainActivity): ProductListAdapter.ProductListCallback =
        mainActivity

    @Provides
    fun provideLinearLayoutManager(mainActivity: MainActivity) =
        LinearLayoutManager(mainActivity)

}