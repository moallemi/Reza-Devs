package me.moallemi.mvvm_mvi.feature.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import me.moallemi.mvvm_mvi.R
import me.moallemi.mvvm_mvi.di.AppCreationExtras
import me.moallemi.mvvm_mvi.domain.Movie

class MvvmFragment : Fragment() {

  private val viewModel by viewModels<MvvmMoviesViewModel>(
    factoryProducer = { MvvmMoviesViewModel.Factory },
    extrasProducer = { AppCreationExtras },
  )

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.fragment_movies, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.title).text = "MVVM"
    view.findViewById<Button>(R.id.addMovie).setOnClickListener {
      viewModel.addMovie(
        Movie(
          title = "Movie ${viewModel.movies.value.size + 1}",
        )
      )
    }

    viewLifecycleOwner.lifecycleScope.launch {

      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.isLoading.collect { isLoading ->
          view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
          view.findViewById<Button>(R.id.addMovie).visibility = if (!isLoading) View.VISIBLE else View.GONE
        }
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.movies.collect { movies ->
          view.findViewById<TextView>(R.id.movies).text = movies.joinToString("\n")
        }
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.loadMovies()
      }
    }
  }

  companion object {
    fun newInstance() = MvvmFragment()
  }
}