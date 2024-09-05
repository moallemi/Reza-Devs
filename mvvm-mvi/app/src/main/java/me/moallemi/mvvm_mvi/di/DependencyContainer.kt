package me.moallemi.mvvm_mvi.di

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import me.moallemi.mvvm_mvi.domain.LoadMoviesRepository
import me.moallemi.mvvm_mvi.domain.LoadMoviesRepositoryImpl
import me.moallemi.mvvm_mvi.domain.LoadMoviesUseCase
import me.moallemi.mvvm_mvi.domain.LoadMoviesUseCaseImpl

class DependencyContainer {

  private val loadMoviesRepository: LoadMoviesRepository by lazy {
    LoadMoviesRepositoryImpl()
  }

  val loadMoviesUseCase: LoadMoviesUseCase by lazy {
    LoadMoviesUseCaseImpl(
      loadMoviesRepository = loadMoviesRepository
    )
  }

  companion object {
    val instance = DependencyContainer()
  }
}

object AppContainerKey : CreationExtras.Key<DependencyContainer>

val AppCreationExtras = MutableCreationExtras().apply {
  set(
    AppContainerKey,
    DependencyContainer.instance,
  )
}