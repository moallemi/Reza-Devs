package me.moallemi.composepagingplayground.domain

import me.moallemi.composepagingplayground.data.SampleRepository

class LoadSampleUseCase(
  private val repository: SampleRepository
) {
  operator fun invoke() = repository.loadItems()
}