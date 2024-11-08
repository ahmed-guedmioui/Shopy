package com.ag_apps.core.domain.models

data class Product(
    val productId: Int,
    val title: String,
    val description: String,
    val brand: String,
    val thumbnail: String,
    val images: List<String>,
    val rating: Float,
    val price: Double,
    val discount: Int,
    val categoryId: Int,
    val categoryName: String,
    val filter: String,
    val selectedFilter: String? = null,
    val filterList: List<String>,
    val isInWishList: Boolean,
    val isInCartList: Boolean
)