package com.spaetimc.presentation.scan

import android.util.Log
import com.commercetools.models.order.Order
import com.google.zxing.Result
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import com.spaetimc.presentation.scan.productlist.ProductListListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.properties.Delegates

class ScanPresenter
@Inject constructor(
    private val scanView: ScanContract.ScanView,
    private val scanProductUseCase: ScanProductUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val compositeDisposable: CompositeDisposable
) : ScanContract.ScanPresenter, ProductListListener {

    private var productList by Delegates.observable(emptyList<AppProduct>()) { _, _, newProductList ->
        scanView.updateProductList(newProductList.sortedByDescending { it.createdAt })
        val totalPriceInCent: Int = newProductList.sumBy { it.priceInCent * it.amount }
        scanView.updateTotalPrice(totalPriceInCent)
    }

    private lateinit var barcodeStream: PublishProcessor<String>

    override fun start() = with(scanView) {
        requestPermissions()
        initializeProductList()
        initOnClickListeners()
        initialiseCodeBarStream()
    }

    private fun initialiseCodeBarStream() {
        barcodeStream = PublishProcessor.create()
        barcodeStream
            .debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapMaybe { scanProductUseCase.getProduct(it).defaultIfEmpty(EMPTY_PRODUCT) }
            .subscribeBy(onNext = ::handleScannedProduct, onError = ::handleScanError)
            .addTo(compositeDisposable)
    }

    override fun handleNewBarcode(barcode: Result?) {
        barcode?.text?.let { barcodeStream.onNext(it) }
    }

    private fun handleScannedProduct(product: AppProduct) {
        Log.d(TAG, product.toString())
        scanView.showProgress()

        if (product === EMPTY_PRODUCT) handleNoProductFound()
        else handleProductFound(product)

        scanView.hideProgress()
        scanView.reStartCamera()
    }

    private fun handleProductFound(product: AppProduct) {
        productList = when (val productInList = productList.find { it.barcode == product.barcode }) {
            null -> productList + product
            else -> changeAmountOf(productInList, withOperation = Int::plus)
        }
    }

    private fun handleNoProductFound() = scanView.showMessage("Sorry, no product found for this barcode.")

    private fun handleScanError(throwable: Throwable) = with(scanView) {
        Log.d(TAG, throwable.message ?: "Message missing")
        showMessage("Something went wrong, try again.")
    }

    private fun changeAmountOf(product: AppProduct, withOperation: Int.(Int) -> Int) =
        productList
            .filterNot { it.barcode == product.barcode }
            .plus(product.copy(amount = product.amount.withOperation(1)))

    override fun onPlusButtonClicked(product: AppProduct) {
        productList = changeAmountOf(product, withOperation = Int::plus)
    }

    override fun onMinusButtonClicked(product: AppProduct) {
        productList =
            if (product.amount <= 1) productList - product
            else changeAmountOf(product, withOperation = Int::minus)
    }

    override fun checkout() =
        if (productList.isEmpty()) scanView.showMessage("Can't checkout an empty cart")
        else scanView.showProgress().also {
            checkoutUseCase.checkout(productList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = ::handleSuccessfulOrder, onError = ::handleCheckoutError)
                .addTo(compositeDisposable)
        }

    private fun handleSuccessfulOrder(order: Order) = with(scanView) {
        hideProgress()
        showCheckoutScreen(order.orderNumber)
    }.also { cancelOrder() }

    private fun handleCheckoutError(throwable: Throwable) = with(scanView) {
        Log.d(TAG, throwable.message ?: "Message missing")
        hideProgress()
        showMessage("Something went wrong, try again.")
    }

    override fun cancelOrder() {
        productList = emptyList()
    }

    override fun stop() = compositeDisposable.dispose()

    companion object {
        private const val TAG = "ScanPresenter"

        private val EMPTY_PRODUCT = AppProduct(
            name = "EMPTY_PRODUCT",
            createdAt = 0,
            pictureUrl = "",
            description = "empty_product",
            amount = 0,
            barcode = "",
            priceInCent = 0
        )
    }

}