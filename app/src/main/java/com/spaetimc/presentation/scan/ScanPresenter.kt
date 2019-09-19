package com.spaetimc.presentation.scan

import com.commercetools.models.product.ProductVariant
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

    private var productList by Delegates.observable(emptyList<AppProduct>()) { _, _, newProductList ->
        scanView.updateProductList(newProductList)
    }

    override fun start() {
        scanView.initializeProductlist()
        scanView.initScanner()
        startScanner()
    }

    private fun startScanner() {
        scanProductUseCase.scanProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { product -> productList += product },
                onError = { scanView.showMessage("Something went wrong, try again") }
            )
            .addTo(compositeDisposable)
    }

    override fun doStuff(): Unit = TODO("just to prepare for callbacks from clicks of the product list")

    override fun stop() = compositeDisposable.dispose()

}