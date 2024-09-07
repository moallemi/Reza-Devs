# Compose Paging Playground

This project demonstrates the use of Jetpack Compose with Paging in a Compose-based Android application.

## YouTube Video
[![Compose Paging Playground on Reza Devs](https://img.youtube.com/vi/l-zShnTiLLY/0.jpg)](https://youtu.be/l-zShnTiLLY)

## Project Structure

- **Domain Layer**
  - `LoadSampleUseCase.kt`: Contains the use case for loading sample data.
- **Data Layer**
  - `SampleData.kt`: Defines the data model for sample data.
  - `SamplePagingSource.kt`: Implements the paging source for loading data from a remote data source.
- **Configuration**
  - `gradle/libs.versions.toml`: Manages the versions of dependencies and plugins used in the project.

## Dependencies

The project uses the following main dependencies:

- **Kotlin**: 1.9.0
- **Android Gradle Plugin**: 8.4.2
- **Jetpack Compose**: 2023.08.00
- **Paging**: 3.2.1

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/moallemi/Reza-Devs.git
    ```
2. Open compose-paging3 project in Android Studio.
3. Sync the project with Gradle files.
4. Build and run the project on an emulator or device.

## Usage

The application demonstrates how to use Jetpack Compose with Paging to load and display paginated data. The `SamplePagingSource` class handles the data loading logic, while the `LoadSampleUseCase` class provides a use case for loading the data.

