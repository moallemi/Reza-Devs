package me.moallemi.composepagingplayground.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface SampleRemoteDataSource {
  suspend fun loadItems(page: Int): Result<List<SampleData>>
}

class SampleRemoteDataSourceImpl : SampleRemoteDataSource {

  override suspend fun loadItems(page: Int): Result<List<SampleData>> =
    withContext(Dispatchers.IO) {
      val delay = if (page == 1) 2000L else 1000L
      delay(delay)

      if (canConnectToInternet()) {
        val items = when {
          page <= 5 -> List(10) { SampleData("Item ${((page - 1) * 10) + it + 1}") }
          page == 6 -> listOf(SampleData("Item 51"), SampleData("Item 52"))
          else -> emptyList()
        }
        Result.success(items)
      } else {
        Result.failure(Exception("Failed to load items"))
      }
    }

  private suspend fun canConnectToInternet(): Boolean =
    withContext(Dispatchers.IO) {
      val httpsURLConnection =
        (URL("https://www.google.com").openConnection() as HttpsURLConnection)
      httpsURLConnection.connectTimeout = 10000
      httpsURLConnection.readTimeout = 10000
      httpsURLConnection.requestMethod = "HEAD"
      val responseCode = try {
        httpsURLConnection.responseCode
      } catch (e: Exception) {
        -1
      }
      responseCode == 200
    }
}