# Trending Movies Android App

## Introduction
Welcome to the Trending Movies Android application! This app provides users with a simple and intuitive interface to explore trending movies and view their details. Utilizing the REST API provided by The Movie Database (TMDb), this app fetches data about trending movies and displays them within the application.

## Task
The primary objective of this project is to create an Android application that showcases a list of trending movies. Upon selecting a movie from the list, users can access additional details about the chosen movie.

## API Integration
As the data provider, we utilize TMDb's REST API endpoints to fetch information about trending movies and their details. Below are the key API endpoints used in this application:

- **List of Trending Movies**: Fetches the list of trending movies.
  - API Name: [Discover Movie](https://api.themoviedb.org/3/discover/movie)
  - API Documentation: [Discover Movie - TMDb](https://developers.themoviedb.org/3/discover/movie-discover)

- **Details of a Movie**: Retrieves detailed information about a specific movie.
  - API Name: [Get Movie Details](https://developers.themoviedb.org/3/movies/{movie_id})
  - API Documentation: [Get Movie Details - TMDb](https://developers.themoviedb.org/3/movies/get-movie-details)

## Notes
- **API Key**: To authenticate requests, an API key should be used.
- **Image Path**: Refer to TMDb's documentation to obtain the full poster image path of a movie: [Getting Started - Images](https://developers.themoviedb.org/3/getting-started/images)

## Implementation
While a reference UI image is provided, developers have the flexibility to implement the UI according to their creativity. However, it's encouraged to maintain a high standard of software quality and usability.

## Remarks
- **Quality Assurance**: Even though this project is simple, strive to implement it in a manner that reflects your understanding of high-quality software development principles.
- **GitHub Repository**: The application code should be hosted on a public GitHub repository, and the repository link should be shared for review.
- **Compatibility**: Ensure that the application is compatible with the latest Android SDK and runs seamlessly on the latest production version of Android Studio.
- **Language**: The application should be written in Kotlin, adhering to best practices and Kotlin conventions.

Thank you for considering our Trending Movies Android App project.
