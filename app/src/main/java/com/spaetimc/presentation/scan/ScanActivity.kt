package com.spaetimc.presentation.scan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.spaetimc.R
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.scan.productlist.ProductListAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_scan.*
import javax.inject.Inject

class ScanActivity : AppCompatActivity(), ScanContract.ScanView {

    @Inject
    lateinit var scanPresenter: ScanContract.ScanPresenter

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var scanProductUsecase: ScanProductUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        scanPresenter.start()
    }

    override fun initializeProductlist() = with(productList) {
        layoutManager = linearLayoutManager
        adapter = productListAdapter
    }

    override fun initScanner(): Unit = Unit // TODO

    override fun updateProductList(productList: List<String>) {
        productListAdapter.productList = productList
    }

    override fun showProgress(): Unit = Unit // TODO

    override fun hideProgress(): Unit = Unit // TODO

    override fun showMessage(message: String?): Unit = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}