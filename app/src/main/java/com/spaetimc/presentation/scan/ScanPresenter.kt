package com.spaetimc.presentation.scan

import android.util.Log
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.commercetools.models.order.Order
import com.google.zxing.Result
import com.spaetimc.domain.CheckoutUseCase
import com.spaetimc.domain.ScanProductUseCase
import com.spaetimc.presentation.scan.model.AppProduct
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class ScanPresenter @Inject constructor(
    private val scanView: ScanContract.ScanView,
    private val scanProductUseCase: ScanProductUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val job: Job
) : ScanContract.ScanPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var productList by Delegates.observable(emptyList<AppProduct>()) { _, _, newProductList ->
        scanView.updateProductList(newProductList.sortedByDescending { it.createdAt })
        val totalPriceInCent: Int = newProductList.sumBy { it.priceInCent * it.amount }
        scanView.updateTotalPrice(totalPriceInCent)
    }

    override fun start() = with(scanView) {
        requestPermissions()
        initializeProductList()
        initOnClickListeners()
    }

    override fun handleNewBarcode(barcode: Result?) {
        barcode?.text?.let { getProductFrom(it) }
    }

    private fun getProductFrom(barcode: String) = launch(coroutineContext + scanErrorHandler) {
        scanView.showProgress()
        val product = withContext(Dispatchers.IO) { scanProductUseCase.getProduct(barcode) }
        handleScannedProduct(product)
    }

    private fun handleScannedProduct(product: Option<AppProduct>) {
        Log.d(TAG, product.toString())

        when (product) {
            is Some -> handleProductFound(product.t)
            is None -> handleNoProductFound()
        }

        scanView.hideProgress()
        scanView.reStartCamera()
    }

    private fun handleProductFound(product: AppProduct) {
        productList = when (val productInList = productList.find { it.barcode == product.barcode }) {
            null -> productList + product
            else -> changeAmountOf(productInList, withOperation = Int::plus)
        }
    }

    private val scanErrorHandler = CoroutineExceptionHandler { _, exception ->
        scanView.hideProgress()
        scanView.showMessage("Something went wrong, try again: ${exception.message ?: "no message"}")
    }

    private fun handleNoProductFound() = scanView.showMessage("Sorry, no product found for this barcode.")

    private fun changeAmountOf(product: AppProduct, withOperation: Int.(Int) -> Int) =
        productList
            .filterNot { it.barcode == product.barcode }
            .plus(product.copy(amount = product.amount.withOperation(1)))

    fun onPlusButtonClicked(product: AppProduct) {
        productList = changeAmountOf(product, withOperation = Int::plus)
    }

    fun onMinusButtonClicked(product: AppProduct) {
        productList =
            if (product.amount <= 1) productList - product
            else changeAmountOf(product, withOperation = Int::minus)
    }

    override fun checkout() {
        if (productList.isEmpty()) scanView.showMessage("Can't checkout an empty cart")
        else launch(coroutineContext + checkoutErrorHandler) {
            val order = withContext(Dispatchers.IO) { checkoutUseCase.checkout(productList) }
            handleSuccessfulOrder(order)
        }
    }

    private fun handleSuccessfulOrder(order: Order) = with(scanView) {
        hideProgress()
        showCheckoutScreen(order.orderNumber)
    }.also { cancelOrder() }

    private val checkoutErrorHandler = CoroutineExceptionHandler { _, exception ->
        scanView.hideProgress()
        scanView.showMessage("Something went wrong, try again: ${exception.message ?: "no message"}")
    }

    override fun cancelOrder() {
        productList = emptyList()
    }

    override fun stop() = job.cancel()

    companion object {
        private const val TAG = "ScanPresenter"
    }

}