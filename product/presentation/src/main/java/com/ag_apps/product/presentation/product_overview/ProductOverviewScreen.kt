package com.ag_apps.product.presentation.product_overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ag_apps.core.presentation.ProductList
import com.ag_apps.core.presentation.ProductsScaffold
import com.ag_apps.core.presentation.designsystem.ShopyTheme
import com.ag_apps.core.presentation.model.ProductUI
import com.ag_apps.product.presentation.R
import org.koin.androidx.compose.koinViewModel

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun ProductOverviewScreenCore(
    viewModel: ProductOverviewViewModel = koinViewModel(),
    appName: String,
    onProductClick: (Int) -> Unit,
    onSearch: () -> Unit
) {

    ProductOverviewScreen(
        state = viewModel.state,
        appName = appName,
        onAction = { action ->
            when (action) {
                is ProductOverviewAction.ClickProduct -> {
                    onProductClick(
                        viewModel.state.products[action.productIndex].productId
                    )
                }

                is ProductOverviewAction.Search -> onSearch()

                else -> viewModel.onAction(action)

            }
        }
    )
}

@Composable
private fun ProductOverviewScreen(
    state: ProductOverviewState,
    appName: String,
    onAction: (ProductOverviewAction) -> Unit,
) {

    ProductsScaffold(
        appName = appName,
        isFilterOpen = state.isFilterOpen,
        minPriceState = state.minPriceState,
        maxPriceState = state.maxPriceState,
        toggleFilter = { onAction(ProductOverviewAction.ToggleFilter) },
        toggleProductsLayout = { onAction(ProductOverviewAction.ToggleProductsLayout) },
        applyFilter = { onAction(ProductOverviewAction.ApplyFilter) },
        onSearch = { onAction(ProductOverviewAction.Search) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isApplyingFilter || state.isLoading && !state.isError && state.products.isEmpty()) {
                CircularProgressIndicator()
            }
            if (state.isError && state.products.isEmpty()) {
                Text(
                    text = stringResource(R.string.can_t_load_products_right_now_please_try_again_later),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (state.products.isNotEmpty() && !state.isApplyingFilter) {
            ProductList(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                products = state.products,
                isGridLayout = state.isGridLayout,
                isLoading = state.isLoading,
                category = state.category,
                onToggleProductInWishlist = { index ->
                    onAction(ProductOverviewAction.ToggleProductInWishlist(index))
                },
                onToggleProductInCart = { index ->
                    onAction(ProductOverviewAction.ToggleProductInCart(index))
                },
                onPaginate = {
                    onAction(ProductOverviewAction.Paginate)
                },
                onProductClick = { index ->
                    onAction(ProductOverviewAction.ClickProduct(index))
                }
            )
        }
    }

}

@Preview
@Composable
private fun ProductOverviewScreenPreview() {
    ShopyTheme {
        ProductOverviewScreen(
            appName = "Shopy",
            state = ProductOverviewState(
                products = products,
                isGridLayout = true
            ),
            onAction = {}
        )
    }
}

val products = listOf(
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
    ProductUI(
        productId = 1,
        title = "Product 1",
        description = "Product 1 description",
        image = "",
        price = 100,
        categoryName = "Category 1",
        isInWishList = false,
        isInCartList = false
    ),
    ProductUI(
        productId = 2,
        title = "Product 2",
        description = "Product 2 description",
        image = "",
        price = 230,
        categoryName = "Category 2",
        isInWishList = true,
        isInCartList = false
    ),
)