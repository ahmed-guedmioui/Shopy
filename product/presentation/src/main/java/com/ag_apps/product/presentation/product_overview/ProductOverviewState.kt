package com.ag_apps.product.presentation.product_overview

import androidx.compose.foundation.text.input.TextFieldState
import com.ag_apps.core.domain.models.Category
import com.ag_apps.core.domain.models.Product

/**
 * @author Ahmed Guedmioui
 */
data class ProductOverviewState(
    val isLoading: Boolean = false,
    val isApplyingFilter: Boolean = false,
    val isError: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val productsOffset: Int = 0,
    val minPriceState: TextFieldState = TextFieldState(""),
    val maxPriceState: TextFieldState = TextFieldState(""),
    val isGridLayout: Boolean = true,
    val isFilterOpen: Boolean = false
)