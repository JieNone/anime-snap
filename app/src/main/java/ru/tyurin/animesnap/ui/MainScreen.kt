package ru.tyurin.animesnap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.tyurin.animesnap.R
import ru.tyurin.animesnap.ui.screens.HomeScreen
import ru.tyurin.animesnap.ui.screens.SearchScreen
import ru.tyurin.animesnap.viewmodels.TitleViewModel


@Composable
fun NavController() {
    val navController = rememberNavController()
    val titleViewModel = hiltViewModel<TitleViewModel>()
    NavHost(navController = navController, startDestination = "anime_title_app") {
        composable("anime_title_app") {
            AnimeTitleApp(
                onNavigateToHomeScreen = { navController.navigate("home_screen") },
                viewModel = titleViewModel
            )
        }
        composable("home_screen") {
            HomeScreen(
                viewModel = titleViewModel,
                retryAction = titleViewModel::getTitleByUrl
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeTitleApp(
    onNavigateToHomeScreen: () -> Unit,
    viewModel: TitleViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AnimeTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SearchScreen(onNavigateToHomeScreen = onNavigateToHomeScreen, titleViewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
