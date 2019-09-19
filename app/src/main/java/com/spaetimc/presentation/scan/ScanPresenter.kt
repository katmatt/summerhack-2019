package com.spaetimc.presentation.scan

import com.google.zxing.Result
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.presentation.scan.productlist.ProductListListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

class ScanPresenter @Inject constructor(
    private val scanView: ScanContract.ScanView,
    private val scanProductUseCase: ScanProductUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val compositeDisposable: CompositeDisposable
) : ScanContract.ScanPresenter, ProductListListener {

    private var productList by Delegates.observable(emptyList<AppProduct>()) { _, _, newProductList ->
        scanView.updateProductList(newProductList.sortedByDescending { it.createdAt })
    }

    override fun start() = with(scanView) {
        initializeProductList()
        initOnClickListners()
    }

    override fun handleNewBarcode(barcode: Result?) {
        barcode?.text?.let { code ->
            scanProductUseCase.getProduct(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { product ->
                        scanView.reStartCamera()
                        if (productList.any { it.barcodeValue == product.barcodeValue }) onPlusButtonClicked(product)
                        else productList = productList + product
                    },
                    onComplete = {
                        scanView.reStartCamera()
                        scanView.showMessage("Sorry, no product found for this barcode.")
                    },
                    onError = { scanView.showMessage("Something went wrong, try again.") }
                )
                .addTo(compositeDisposable)
        }
    }

    override fun onPlusButtonClicked(product: AppProduct) {

        val currentAmmount = productList.find { it.barcodeValue == product.barcodeValue }?.amount ?: 1
        productList = productList
            .filterNot { it.barcodeValue == product.barcodeValue }
            .plus(product.copy(amount = currentAmmount + 1))

    }

    override fun onMinusButtonClicked(product: AppProduct) {
        val currentAmmount = productList.find { it.barcodeValue == product.barcodeValue }?.amount ?: 1
        productList =
            if (currentAmmount <= 1) productList - product
            else productList
                .filterNot { it.barcodeValue == product.barcodeValue }
                .plus(product.copy(amount = currentAmmount - 1))
    }

    override fun checkout() {
        checkoutUseCase.checkout(productList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { scanView.showCheckoutScreen(it.orderNumber) },
                onError = { scanView.showMessage("Something went wrong, try again.") }
            )
            .addTo(compositeDisposable)
    }

    override fun cancelOrder() {
        productList = emptyList()
    }

    override fun stop() = compositeDisposable.dispose()

    companion object {
        private const val TAG = "ScanPresenter"
    }

}