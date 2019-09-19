package com.spaetimc.presentation.scan.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spaetimc.R
import kotlinx.android.synthetic.main.product_list_entry.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ProductListAdapter @Inject constructor(
    private val productListListener: ProductListListener
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productList by Delegates.observable(emptyList<String>()) { _, _, _ -> notifyDataSetChanged() }

    private val onClickListener: View.OnClickListener by lazy {
        View.OnClickListener { productListListener.doStuff() }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.productName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.product_list_entry, parent, false)
        .let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder) {
            title.text = productList[position]
            with(view) {
                tag = title
                setOnClickListener(onClickListener)
            }
        }

    override fun getItemCount() = productList.size

}