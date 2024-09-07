package me.moallemi.mvvm_mvi.feature.mvi

import me.moallemi.mvvm_mvi.domain.Movie

data class MovieListUiState(
  val loading: Boolean = false,
  val movies: List<Movie> = emptyList()
)
