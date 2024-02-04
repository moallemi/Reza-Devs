package me.moallemi.composepagingplayground.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface SampleRepository {
  fun loadItems(): Flow<PagingData<SampleData>>
}

class SampleRepositoryImpl(
  private val remoteDataSource: SampleRemoteDataSource
) : SampleRepository {

  override fun loadItems(): Flow<PagingData<SampleData>> =
    Pager(
      config = PagingConfig(pageSize = SamplePagingSource.PAGE_SIZE),
      pagingSourceFactory = { SamplePagingSource(remoteDataSource) }
    ).flow
}