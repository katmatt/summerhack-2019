package com.spaetimc.presentation.scan.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spaetimc.R
import com.spaetimc.presentation.scan.model.AppProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_list_entry.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ProductListAdapter @Inject constructor(
    private val productListListener: ProductListListener
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productList by Delegates.observable(emptyList<AppProduct>()) { _, _, _ -> notifyDataSetChanged() }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.productImage
        val productName: TextView = view.productName
        val productDescription: TextView = view.productDescription
        val productPrice: TextView = view.productPrice
        val plusButton: ImageButton = view.plusButton
        val minusButton: ImageButton = view.minusButton
        val productCounter: TextView = view.productCounter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.product_list_entry, parent, false)
        .let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder) {
            Picasso.get()
                .load(productList[position].pictureUrl)
                .resize(112, 112)
                .centerCrop()
                .placeholder(R.drawable.product_placeholder)
                .into(productImage)

            productName.text = productList[position].name
            productDescription.text = productList[position].description
            productPrice.text = productList[position].price
            productCounter.text = productList[position].amount.toString()
            plusButton.setOnClickListener { productListListener.onPlusButtonClicked(productList[position]) }
            minusButton.setOnClickListener { productListListener.onMinusButtonClicked(productList[position]) }

            // leave it here for the moment for later use, when there is a product page
//            with(view) {
//                tag = productName
//                setOnClickListener { productListListener.doStuff() }
//            }
        }

    override fun getItemCount() = productList.size

}