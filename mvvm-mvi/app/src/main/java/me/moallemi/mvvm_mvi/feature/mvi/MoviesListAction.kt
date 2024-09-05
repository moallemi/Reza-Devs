package me.moallemi.mvvm_mvi.feature.mvi

import me.moallemi.mvvm_mvi.domain.Movie

sealed class MoviesListAction {
  data object LoadMovies : MoviesListAction()
  data class AddMovie(val movie: Movie) : MoviesListAction()
}
