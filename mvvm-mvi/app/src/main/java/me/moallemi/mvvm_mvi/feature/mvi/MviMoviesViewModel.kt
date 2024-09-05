package me.moallemi.mvvm_mvi.feature.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.moallemi.mvvm_mvi.di.AppContainerKey
import me.moallemi.mvvm_mvi.di.DependencyContainer
import me.moallemi.mvvm_mvi.domain.LoadMoviesUseCase
import me.moallemi.mvvm_mvi.domain.Movie

class MviMoviesViewModel(
  private val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val moviesState = MutableStateFlow<List<Movie>>(emptyList())

  val state: StateFlow<MovieListUiState> = combine(
    loadingState,
    moviesState,
    ::MovieListUiState
  ).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = MovieListUiState(),
  )

  fun onAction(action: MoviesListAction) {
    when (action) {
      is MoviesListAction.LoadMovies -> loadMovies()
      is MoviesListAction.AddMovie -> addMovie(action.movie)
    }
  }

  private fun loadMovies() {
    viewModelScope.launch {
      loadingState.update { true }
      val result = loadMoviesUseCase()
      moviesState.update { result }
      loadingState.update { false }
    }
  }

  // region: CRUD operations
  private fun addMovie(movie: Movie) {
    viewModelScope.launch {
      moviesState.update { it + movie }
    }
  }

  private fun removeMovie(movie: Movie) {
    viewModelScope.launch {
      moviesState.update { it - movie }
    }
  }

  private fun clearMovies() {
    viewModelScope.launch {
      moviesState.update { emptyList() }
    }
  }

  private fun updateMovie(oldMovie: Movie, newMovie: Movie) {
    viewModelScope.launch {
      moviesState.update { it.map { if (it == oldMovie) newMovie else it } }
    }
  }

  // endregion

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val appContainer = this[AppContainerKey] as DependencyContainer
        val domainDependencyContainer = appContainer.loadMoviesUseCase
        MviMoviesViewModel(
          loadMoviesUseCase = domainDependencyContainer
        )
      }
    }
  }

}