package me.moallemi.mvvm_mvi.feature.mvvm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.moallemi.mvvm_mvi.di.AppCreationExtras
import me.moallemi.mvvm_mvi.domain.Movie
import me.moallemi.mvvm_mvi.feature.mvi.Header

@Composable
fun MvvmScreen(
  modifier: Modifier = Modifier,
) {
  val viewModel = viewModel<MvvmMoviesViewModel>(
    factory = MvvmMoviesViewModel.Factory,
    extras = AppCreationExtras,
  )

  val isLoading by viewModel.isLoading.collectAsState()
  val movies by viewModel.movies.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadMovies()
  }

  MoviesList(
    modifier = modifier,
    isLoading = isLoading,
    movies = movies,
    onAddMovieClick = { viewModel.addMovie(Movie("Movie ${movies.size + 1}")) },
    onRemoveMovieClick = viewModel::removeMovie,
    onRefreshClick = viewModel::loadMovies,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(
  isLoading: Boolean,
  movies: List<Movie>,
  modifier: Modifier = Modifier,
  onAddMovieClick: () -> Unit,
  onRemoveMovieClick: (Movie) -> Unit,
  onRefreshClick: () -> Unit,
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
  ) {
    stickyHeader {
      Header(
        onAddMovieClick = onAddMovieClick,
      )
    }

    if (isLoading) {
      item {
        CircularProgressIndicator(
          modifier = Modifier
            .fillParentMaxWidth()
            .wrapContentWidth(),
        )
      }
    }

    items(movies) { movie ->
      Text(text = movie.title)
    }
  }
}