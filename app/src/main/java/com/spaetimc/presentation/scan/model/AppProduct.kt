package com.spaetimc.presentation.scan.model

data class AppProduct(
    val createdAt: Long,
    val name: String,
    val description: String,
    val priceInCent: Int,
    val pictureUrl: String,
    val barcodeValue: String,
    val amount: Int = 1
)