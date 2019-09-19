package com.spaetimc.presentation.scan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.Result
import com.spaetimc.R
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.checkout.CheckoutActivity
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.presentation.scan.productlist.ProductListAdapter
import com.spaetimc.utils.format
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

class ScanActivity : AppCompatActivity(), ScanContract.ScanView, ZXingScannerView.ResultHandler {

    @Inject
    lateinit var scanPresenter: ScanContract.ScanPresenter

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var scanProductUseCase: ScanProductUseCase

    private var cameraId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        cameraId = savedInstanceState?.getInt("CAMERA_ID", -1) ?: cameraId

        scanPresenter.start()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera(cameraId)
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scanPresenter.stop()
    }

    override fun initializeProductList() = with(productList) {
        layoutManager = linearLayoutManager
        adapter = productListAdapter
    }

    override fun updateTotalPrice(centAmount: Int) {
        totalView.text = "Total ${centAmount.format()}€"
    }

    override fun initOnClickListeners() {
        checkoutButton.setOnClickListener { scanPresenter.checkout() }
        cancelButton.setOnClickListener { scanPresenter.cancelOrder() }
    }

    override fun handleResult(barcode: Result?) = scanPresenter.handleNewBarcode(barcode)

    override fun reStartCamera() = scannerView.resumeCameraPreview(this)

    override fun showCheckoutScreen(orderNumber: String) =
        Intent(this, CheckoutActivity::class.java)
            .also { it.putExtra("orderNumber", orderNumber) }
            .let { intent -> startActivity(intent) }

    override fun updateProductList(productList: List<AppProduct>) {
        productListAdapter.updateList(productList)
    }

    override fun showMessage(message: String?): Unit = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun showProgress(): Unit = Unit // TODO

    override fun hideProgress(): Unit = Unit // TODO

}