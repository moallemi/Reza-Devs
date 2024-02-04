package me.moallemi.composepagingplayground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import me.moallemi.composepagingplayground.data.SampleRemoteDataSourceImpl
import me.moallemi.composepagingplayground.data.SampleRepositoryImpl
import me.moallemi.composepagingplayground.domain.LoadSampleUseCase

class MainViewModel : ViewModel() {

  val sampleDataUseCase = LoadSampleUseCase(
    repository = SampleRepositoryImpl(
      remoteDataSource = SampleRemoteDataSourceImpl()
    )
  )

  val pagedData = sampleDataUseCase()
    .cachedIn(viewModelScope)
}