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

    override fun start() = with(scanView) {
        initializeProductList()
        initOnClickListeners()
    }

    override fun handleNewBarcode(barcode: Result?) {
        barcode?.text?.let { code ->
            scanProductUseCase.getProduct(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = ::handleScannedProduct,
                    onComplete = {
                        scanView.showMessage("Sorry, no product found for this barcode.")
                        scanView.reStartCamera()
                    },
                    onError = {
                        scanView.showMessage("Something went wrong, try again.")
                        scanView.reStartCamera()
                    }
                )
                .addTo(compositeDisposable)
        }
    }

    private fun handleScannedProduct(product: AppProduct) {
        val productInList = productList.find { it.barcode == product.barcode }
        productList =
            if (productInList != null) {
                changeAmountOf(productInList, withOperation = Int::plus)
            } else {
                productList + product
            }
        scanView.reStartCamera()
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

    override fun checkout() {
        if(productList.isEmpty()){
            scanView.showMessage("Can't checkout an empty cart")
            return
        }
        checkoutUseCase.checkout(productList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    scanView.showCheckoutScreen(it.orderNumber)
                    cancelOrder()
                },
                onError = { scanView.showMessage("Something went wrong, try again.") }
            )
            .addTo(compositeDisposable)
    }

    override fun cancelOrder() {
        productList = emptyList()
    }

    override fun stop() = compositeDisposable.dispose()

}