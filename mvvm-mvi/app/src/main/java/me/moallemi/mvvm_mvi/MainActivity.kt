package me.moallemi.mvvm_mvi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import me.moallemi.mvvm_mvi.feature.mvi.MviFragment
import me.moallemi.mvvm_mvi.feature.mvvm.MvvmFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container_1, MvvmFragment.newInstance())
        .replace(R.id.fragment_container_2, MviFragment.newInstance())
        .commitAllowingStateLoss()
    }
  }
}
