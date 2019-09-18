package com.spaetimc.scan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.R
import com.spaetimc.domain.ScanProductUseCase
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ProductListAdapter.ProductListCallback {

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var scanProductUsecase: ScanProductUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(productList) {
            layoutManager = linearLayoutManager
            adapter = productListAdapter
        }

        productListAdapter.productList = listOf("Product 1", "Product 2")
    }

    override fun doStuff(): Unit =
        TODO("just to prepare for callbacks from clicks of the product list")

}