package com.spaetimc.presentation.checkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spaetimc.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_checkout.*
import javax.inject.Inject

class CheckoutActivity : AppCompatActivity(), CheckoutContract.CheckoutView {

    @Inject
    lateinit var checkoutPresenter: CheckoutContract.CheckoutPresenter

    private val orderNumber by lazy { intent.getStringExtra("orderNumber") }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        checkoutText.text = "Your order with the number $orderNumber has successfully been set."

        checkoutPresenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        checkoutPresenter.stop()
    }

}
