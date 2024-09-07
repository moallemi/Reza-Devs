package me.moallemi.mvvm_mvi.feature.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.moallemi.mvvm_mvi.di.AppContainerKey
import me.moallemi.mvvm_mvi.di.DependencyContainer
import me.moallemi.mvvm_mvi.domain.LoadMoviesUseCase
import me.moallemi.mvvm_mvi.domain.Movie

class MvvmMoviesViewModel(
  private val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  val isLoading = loadingState.asStateFlow()

  private val moviesState = MutableStateFlow<List<Movie>>(emptyList())
  val movies = moviesState.asStateFlow()


  fun loadMovies() {
    viewModelScope.launch {
      loadingState.update { true }
      val result = loadMoviesUseCase()
      moviesState.update { result }
      loadingState.update { false }
    }
  }

  // region: CRUD operations
  fun addMovie(movie: Movie) {
    viewModelScope.launch {
      moviesState.update { it + movie }
    }
  }

  fun removeMovie(movie: Movie) {
    viewModelScope.launch {
      moviesState.update { it - movie }
    }
  }

  fun clearMovies() {
    viewModelScope.launch {
      moviesState.update { emptyList() }
    }
  }

  fun updateMovie(oldMovie: Movie, newMovie: Movie) {
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
        MvvmMoviesViewModel(
          loadMoviesUseCase = domainDependencyContainer
        )
      }
    }
  }

}