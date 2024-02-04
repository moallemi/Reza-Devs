package me.moallemi.composepagingplayground.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SamplePagingSource(
  private val remoteDataSource: SampleRemoteDataSource
) : PagingSource<Int, SampleData>() {
  override fun getRefreshKey(state: PagingState<Int, SampleData>): Int? = START_PAGE

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SampleData> {
    val page = params.key ?: START_PAGE
    val result = remoteDataSource.loadItems(page)

    return result.fold(
      onSuccess = { data ->
        LoadResult.Page(
          data = data,
          prevKey = if (page == START_PAGE) null else page - 1,
          nextKey = if (data.isEmpty()) null else page + 1
        )
      },
      onFailure = { error ->
        LoadResult.Error(error)
      }
    )
  }

  companion object {
    const val START_PAGE = 1
    const val PAGE_SIZE = 10
  }

}