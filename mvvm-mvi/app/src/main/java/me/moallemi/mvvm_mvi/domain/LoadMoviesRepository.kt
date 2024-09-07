package me.moallemi.mvvm_mvi.domain

interface LoadMoviesRepository {
  suspend fun loadMovies(): List<Movie>
}

class LoadMoviesRepositoryImpl : LoadMoviesRepository {
  override suspend fun loadMovies(): List<Movie> =
    listOf(
      Movie("The Shawshank Redemption"),
      Movie("The Godfather"),
      Movie("The Dark Knight"),
      Movie("The Godfather: Part II"),
      Movie("The Lord of the Rings: The Return of the King"),
      Movie("Pulp Fiction"),
      Movie("Schindler's List"),
    )
}