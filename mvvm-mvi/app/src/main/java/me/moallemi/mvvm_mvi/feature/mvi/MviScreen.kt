package me.moallemi.mvvm_mvi.feature.mvi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.moallemi.mvvm_mvi.di.AppCreationExtras
import me.moallemi.mvvm_mvi.domain.Movie
import me.moallemi.mvvm_mvi.feature.mvi.MoviesListAction.LoadMovies

@Composable
fun MviScreen(
  modifier: Modifier = Modifier,
) {
  val viewModel = viewModel<MviMoviesViewModel>(
    factory = MviMoviesViewModel.Factory,
    extras = AppCreationExtras,
  )

  val state by viewModel.state.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.onAction(LoadMovies)
  }

  MoviesList(
    modifier = modifier,
    state = state,
    onAction = viewModel::onAction,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(
  state: MovieListUiState,
  modifier: Modifier = Modifier,
  onAction: (MoviesListAction) -> Unit,
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
  ) {
    stickyHeader {
      Header(
        onAddMovieClick = { onAction(MoviesListAction.AddMovie(Movie("Movie ${state.movies.size + 1}"))) }
      )
    }

    if (state.loading) {
      item {
        CircularProgressIndicator(
          modifier = Modifier
            .fillParentMaxWidth()
            .wrapContentWidth(),
        )
      }
    }

    items(state.movies) { movie ->
      Text(text = movie.title)
    }
  }
}

@Composable
fun Header(
  modifier: Modifier = Modifier,
  onAddMovieClick: () -> Unit,
) {

  Row(
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = "MVI",
      style = MaterialTheme.typography.titleLarge,
    )

    Spacer(modifier = Modifier.weight(1f))

    Button(
      onClick = onAddMovieClick,
    ) {
      Text(text = "Add Movie")
    }
  }
}