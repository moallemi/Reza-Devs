package me.moallemi.mvvm_mvi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import me.moallemi.mvvm_mvi.feature.mvi.MviFragment
import me.moallemi.mvvm_mvi.feature.mvi.MviScreen
import me.moallemi.mvvm_mvi.feature.mvvm.MvvmFragment
import me.moallemi.mvvm_mvi.feature.mvvm.MvvmScreen

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContentView(R.layout.activity_main)

    val showFragment = true

    if (showFragment) {
      if (savedInstanceState == null) {
        supportFragmentManager.beginTransaction()
          .replace(R.id.fragment_container_1, MvvmFragment.newInstance())
          .replace(R.id.fragment_container_2, MviFragment.newInstance())
          .commitAllowingStateLoss()
      }
    } else {
      setContent {
        MaterialTheme {
          Surface {
            Scaffold { innerPadding ->
              Column(
                modifier = Modifier.padding(innerPadding),
              ) {
                MvvmScreen(
                  modifier = Modifier.weight(1f)
                )
                MviScreen(
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }
        }
      }
    }
  }
}
