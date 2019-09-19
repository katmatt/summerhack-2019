package com.spaetimc.presentation.scan

import com.google.zxing.Result
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
    private val compositeDisposable: CompositeDisposable
) : ScanContract.ScanPresenter, ProductListListener {

    private var productList by Delegates.observable(emptyList<AppProduct>()) { property, _, newProductList ->
        scanView.updateProductList(newProductList)
    }

    override fun start() {
        scanView.initializeProductList()
        scanView.initOnClickListners()
        scanView.initScanner()
    }

    override fun handleNewBarcode(barcode: Result?) {
        barcode?.text?.let { code ->
            scanProductUseCase.getProduct(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { product ->
                        scanView.reStartCamera()
                        productList += product
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
        val produit = productList
            .find { it.barcodeValue == product.barcodeValue }
            ?.copy(amount = product.amount + 1)
            ?.also { updatedProduct ->
                productList = productList - product + updatedProduct
            }
    }


    override fun onMinusButtonClicked(product: AppProduct) = Unit // TODO("not implemented")

    override fun checkout() = Unit // TODO("not implemented")

    override fun cancelOrder() = Unit // TODO("not implemented")

    override fun stop() = compositeDisposable.dispose()

    companion object {
        private const val TAG = "ScanPresenter"
    }

}