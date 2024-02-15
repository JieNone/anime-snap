package ru.tyurin.animesnap.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes {
    MyList,
    Search,
    Downloads,
    Detail
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

val NAV_ITEMS = listOf(
    NavItem(
        label = "My List",
        icon = Icons.AutoMirrored.Default.List,
        route = Routes.MyList.name
    ),
    NavItem(
        label = "Search",
        icon = Icons.Default.Search,
        route = Routes.Search.name
    ),
    NavItem(
        label = "Downloads",
        icon = Icons.Default.Favorite,
        route = Routes.Downloads.name
    )
)