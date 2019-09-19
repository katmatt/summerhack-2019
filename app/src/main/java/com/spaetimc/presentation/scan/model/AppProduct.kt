package com.spaetimc.presentation.scan.model

data class AppProduct(
    val createdAt: Long,
    val name: String,
    val description: String,
    val price: String,
    val pictureUrl: String,
    val barcodeValue: String,
    val amount: Int = 1
)