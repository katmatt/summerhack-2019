package com.spaetimc.domain

import com.spaetimc.presentation.scan.model.AppProduct
import io.reactivex.Flowable
import javax.inject.Inject

class ScanProductUseCaseImpl @Inject constructor() : ScanProductUseCase {

    override fun scanProduct(): Flowable<AppProduct> = Flowable.just(
        AppProduct(
            name = "Club Mate",
            description = "0,5ml",
            price = "0,99",
            pictureUrl = "https://cdn.shopify.com/s/files/1/0665/6951/products/CMORIG16_grande.png?v=1458595085",
            amount = 1
        ),
        AppProduct(
            name = "Mio Mate",
            description = "0,5ml",
            price = "0,99",
            pictureUrl = "https://officedrink.de/shop/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/m/i/mio_mio_mate_12x0_5l_kasten_glas_02.jpeg",
            amount = 1
        )
    )

}