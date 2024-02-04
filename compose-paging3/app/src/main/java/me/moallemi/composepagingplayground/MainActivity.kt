package me.moallemi.composepagingplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import me.moallemi.composepagingplayground.ui.theme.ComposePagingPlaygroundTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ComposePagingPlaygroundTheme {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

          val viewModel: MainViewModel by viewModels()
          val pagedData = viewModel.pagedData.collectAsLazyPagingItems()

          Box(
            modifier = Modifier.fillMaxSize(),
          ) {

            if (pagedData.loadState.refresh is LoadState.Loading) {
              CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
              )
            }

            if (pagedData.loadState.refresh is LoadState.Error) {
              Column {
                Text(
                  modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                  text = "Error: ${pagedData.loadState.refresh}",
                )

                Button(
                  onClick = { pagedData.refresh() }
                ) {
                  Text("Reload")
                }
              }
            }

            LazyColumn(
              contentPadding = innerPadding,
            ) {
              items(
                count = pagedData.itemCount,
                key = { index -> pagedData[index]?.id ?: index }) { item ->
                pagedData[item]?.let { data ->
                  Text(
                    modifier = Modifier
                      .fillMaxSize()
                      .padding(16.dp),
                    text = data.name,
                  )
                }
              }

              if (pagedData.loadState.append is LoadState.Loading) {
                item {
                  CircularProgressIndicator(
                    modifier = Modifier
                      .fillMaxWidth()
                      .wrapContentSize(Alignment.Center),
                  )
                }
              }

              if (pagedData.loadState.append is LoadState.Error) {
                item {
                  Button(
                    modifier = Modifier
                      .fillMaxWidth()
                      .wrapContentSize(Alignment.Center),
                    onClick = { pagedData.retry() }
                  ) {
                    Text("Retry")
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}