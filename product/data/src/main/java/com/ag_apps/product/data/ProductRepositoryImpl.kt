package com.ag_apps.product.data

import com.ag_apps.core.domain.Product
import com.ag_apps.core.domain.ProductDataSource
import com.ag_apps.core.domain.UserDataSource
import com.ag_apps.core.domain.util.DataError
import com.ag_apps.core.domain.util.Result
import com.ag_apps.product.domain.ProductRepository

/**
 * @author Ahmed Guedmioui
 */
class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val userDataSource: UserDataSource
) : ProductRepository {

    override suspend fun getProducts(
        offset: Int,
        minPrice: Int?,
        maxPrice: Int?,
    ): Result<List<Product>, DataError.Network> {

        val userResult = userDataSource.getUser()

        val productsResult = productDataSource.getProducts(
            offset = offset,
            minPrice = minPrice,
            maxPrice = maxPrice
        )

        if (userResult is Result.Success && productsResult is Result.Success) {
            val productsForUser = productsResult.data.map { product ->
                if (userResult.data.wishlist.contains(product.productId.toString())) {
                    product.copy(isInWishList = true)
                } else if (userResult.data.cart.contains(product.productId.toString())) {
                    product.copy(isInCartList = true)
                } else {
                    product
                }
            }

            return Result.Success(productsForUser)
        }

        return productsResult

    }

    override suspend fun addProductToWishlist(
        productId: String
    ): Result<Unit, DataError.Network> {
        return userDataSource.addProductToWishlist(productId)
    }

    override suspend fun removeProductFromWishlist(
        productId: String
    ): Result<Unit, DataError.Network> {
        return userDataSource.removeProductToWishlist(productId)
    }

    override suspend fun addProductToCart(
        productId: String
    ): Result<Unit, DataError.Network> {
        return userDataSource.addProductToCart(productId)
    }

    override suspend fun removeProductFromCart(
        productId: String
    ): Result<Unit, DataError.Network> {
        return userDataSource.removeProductToCart(productId)
    }
}