package me.moallemi.mvvm_mvi.domain

import kotlinx.coroutines.delay

interface LoadMoviesUseCase {
  suspend operator fun invoke(): List<Movie>
}

class LoadMoviesUseCaseImpl(
  private val loadMoviesRepository: LoadMoviesRepository
) : LoadMoviesUseCase {
  override suspend fun invoke(): List<Movie> {
    delay(2000)
    return loadMoviesRepository.loadMovies()
  }
}