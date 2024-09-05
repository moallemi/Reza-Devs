package me.moallemi.mvvm_mvi.feature.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import me.moallemi.mvvm_mvi.R
import me.moallemi.mvvm_mvi.di.AppCreationExtras
import me.moallemi.mvvm_mvi.domain.Movie
import me.moallemi.mvvm_mvi.feature.mvi.MoviesListAction.AddMovie
import me.moallemi.mvvm_mvi.feature.mvvm.MvvmFragment
import me.moallemi.mvvm_mvi.feature.mvvm.MvvmMoviesViewModel

class MviFragment : Fragment() {

  private val viewModel by viewModels<MviMoviesViewModel>(
    factoryProducer = { MviMoviesViewModel.Factory },
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

    view.findViewById<TextView>(R.id.title).text = "MVI"
    view.findViewById<Button>(R.id.addMovie).setOnClickListener {
      viewModel.onAction(
        AddMovie(
          Movie(
            title = "Movie ${viewModel.state.value.movies.size + 1}",
          )
        )
      )
    }

    viewLifecycleOwner.lifecycleScope.launch {

      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.state.collect { state ->

          view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (state.loading) View.VISIBLE else View.GONE
          view.findViewById<Button>(R.id.addMovie).visibility = if (!state.loading) View.VISIBLE else View.GONE
          view.findViewById<TextView>(R.id.movies).text = state.movies.joinToString("\n")
        }
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.onAction(MoviesListAction.LoadMovies)
      }
    }
  }

  companion object {
    fun newInstance() = MviFragment()
  }
}