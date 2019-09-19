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
import com.spaetimc.utils.format
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_list_entry.view.*
import javax.inject.Inject

class ProductListAdapter @Inject constructor(
    private val productListListener: ProductListListener
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var productList = emptyList<AppProduct>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.productImage
        val productName: TextView = view.productName
        val productDescription: TextView = view.productDescription
        val productPrice: TextView = view.productPrice
        val plusButton: ImageButton = view.plusButton
        val minusButton: ImageButton = view.minusButton
        val productCounter: TextView = view.productCounter
    }

    fun updateList(products: List<AppProduct>) {
        productList = products
        notifyDataSetChanged()
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
                .placeholder(R.drawable.ic_placeholder_pic2)
                .into(productImage)

            productName.text = productList[position].name
            productDescription.text = productList[position].description
            productPrice.text = productList[position].priceInCent.format()
            productCounter.text = productList[position].amount.toString()
            plusButton.setOnClickListener { productListListener.onPlusButtonClicked(productList[position]) }
            minusButton.setOnClickListener { productListListener.onMinusButtonClicked(productList[position]) }
        }

    override fun getItemCount() = productList.size

}