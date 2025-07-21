# MovieApp KMP - Kotlin Multiplatform Movie Application

![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-blue?logo=kotlin)
![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4?logo=jetpackcompose)
![TMDB](https://img.shields.io/badge/TMDB-API-01b4e4)

A modern movie discovery application built with Kotlin Multiplatform and Compose Multiplatform, featuring shared UI and business logic across iOS and Android platforms.

## 🎬 Features

- **Cross-platform UI**: Native-like experience with shared Compose Multiplatform UI
- **Movie Discovery**: Browse popular, top-rated, and now-playing movies
- **Advanced Search**: Real-time movie search with debounced queries
- **Movie Details**: Comprehensive movie information with beautiful imagery
- **Favorites System**: Add and manage favorite movies with local storage
- **Modern Design**: Dark theme with gradient backgrounds and smooth animations
- **Responsive Layout**: Adaptive design for different screen sizes

## 🛠️ Technology Stack

### **Core Framework**
- **Kotlin Multiplatform**: Cross-platform code sharing
- **Compose Multiplatform**: Declarative UI framework

### **Network & Data**
- **Ktor Client**: HTTP client for API requests
- **Kotlinx Serialization**: JSON serialization/deserialization
- **Multiplatform Settings**: Cross-platform local storage
- **TMDB API**: The Movie Database integration

### **Architecture & DI**
- **PreCompose**: Navigation and ViewModel management
- **Koin**: Dependency injection framework
- **MVVM Pattern**: Clean architecture with reactive state management

### **UI & Media**
- **Kamel**: Async image loading with caching
- **Material 3**: Modern design system
- **Custom Animations**: Smooth transitions and interactions

## 📱 Supported Platforms

- **Android** (API 24+)
- **iOS** (iOS 18.2+)

## 🏗️ Project Structure

```
movieAppKmp/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/kotlin/
│   │   │   ├── com/ismailtaspinar/movieAppKmp/
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/           # API service (MovieApi)
│   │   │   │   │   ├── local/         # Local storage (FavoritesRepository)
│   │   │   │   │   ├── model/         # Data models (Movie, MovieResponse)
│   │   │   │   │   └── repo/          # Repository implementations
│   │   │   │   ├── di/                # Dependency injection modules
│   │   │   │   ├── navigation/        # Navigation setup and screens
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/    # Reusable UI components
│   │   │   │   │   ├── screens/       # Screen composables
│   │   │   │   │   ├── theme/         # App theming and colors
│   │   │   │   │   └── viewModel/     # ViewModels for each screen
│   │   │   │   ├── utils/             # Utility classes and extensions
│   │   │   │   └── config/            # App configuration
│   │   │   └── composeResources/      # Shared resources
│   │   ├── androidMain/kotlin/        # Android-specific implementations
│   │   └── iosMain/kotlin/            # iOS-specific implementations
│   └── build.gradle.kts               # Multiplatform module configuration
├── iosApp/                            # iOS application wrapper
├── gradle/                            # Gradle wrapper and version catalog
├── .gitignore
├── build.gradle.kts                   # Root build configuration
├── gradle.properties                  # Gradle properties
└── settings.gradle.kts                # Project settings
```

## 🚀 Getting Started

### Prerequisites
- **JDK 11** or higher
- **Android Studio Hedgehog** or later
- **Xcode 15** or later (for iOS development)
- **Kotlin Multiplatform Mobile plugin**
- **TMDB API Key** (register at [themoviedb.org](https://www.themoviedb.org/))

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/movieapp-kmp.git
   cd movieapp-kmp
   ```

2. **Configure API Key**
   Create a `local.properties` file in the root directory:
   ```properties
   tmdb.api.key=your_tmdb_api_key_here
   ```

3. **Run on Android**
   ```bash
   ./gradlew :composeApp:installDebug
   ```

4. **Run on iOS**
   - Open `iosApp/iosApp.xcodeproj` in Xcode
   - Build and run the project

### Development Setup

For development, ensure you have the latest versions of:
- Kotlin Multiplatform Mobile plugin in Android Studio
- CocoaPods (for iOS dependencies)
- Xcode Command Line Tools

## 🔧 Configuration

### API Configuration
The app uses TMDB (The Movie Database) API. Configure your API key in the platform-specific implementations:

**Android**: `composeApp/src/androidMain/kotlin/com/ismailtaspinar/movieAppKmp/config/AppConfig.kt`
**iOS**: `composeApp/src/iosMain/kotlin/com/ismailtaspinar/movieAppKmp/config/AppConfig.kt`

### Dependency Injection
Koin modules are organized in the `di/` package:
- `KoinModule.kt`: Main app module with core dependencies
- `KamelModule.kt`: Image loading configuration
- `NetworkModule.kt`: Platform-specific HTTP client setup

### Network Configuration
Ktor client is configured with:
- Content negotiation for JSON
- Platform-specific engines (CIO for common, Darwin for iOS)
- Automatic retry logic
- Response caching

## 📦 Key Dependencies

```kotlin
// Multiplatform UI
implementation("org.jetbrains.compose.ui:ui:$compose_version")
implementation("org.jetbrains.compose.material3:material3:$compose_version")

// Navigation & ViewModel
api("moe.tlaster:precompose:$precompose_version")
api("moe.tlaster:precompose-viewmodel:$precompose_version")

// Network
implementation("io.ktor:ktor-client-core:$ktor_version")
implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

// Image Loading
implementation("media.kamel:kamel-image:$kamel_version")

// Dependency Injection
implementation("io.insert-koin:koin-core:$koin_version")
implementation("io.insert-koin:koin-compose:$koin_version")

// Local Storage
implementation("com.russhwolf:multiplatform-settings:$settings_version")

// Serialization
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")
```

## 🏛️ Architecture

The application follows **MVVM (Model-View-ViewModel)** architecture pattern with reactive state management:

### Architecture Layers

**Presentation Layer**
- `ui/screens/`: Screen composables (HomeScreen, SearchScreen, MovieDetailScreen, FavoritesScreen)
- `ui/components/`: Reusable UI components (MovieCard, SearchMovieItem)
- `ui/viewModel/`: ViewModels with reactive state using StateFlow
- `ui/theme/`: App theming, colors, and typography

**Data Layer**
- `data/api/`: API service implementations (MovieApi)
- `data/repo/`: Repository pattern implementations (MovieRepository)
- `data/local/`: Local storage management (FavoritesRepository)
- `data/model/`: Data models and DTOs

**Domain Logic**
- Most business logic is shared in the common module
- ViewModels handle UI state and business operations
- Repositories abstract data sources (API + local storage)

### Data Flow
```
UI (Compose) ←→ ViewModel (PreCompose) ←→ Repository ←→ API (Ktor)
                     ↓                           ↓
               StateFlow/State              Local Storage
                                        (Multiplatform Settings)
```

### Key Architectural Decisions

- **Shared UI**: 100% shared Compose Multiplatform UI
- **Reactive State**: StateFlow for reactive UI updates
- **Repository Pattern**: Clean separation of data sources
- **Dependency Injection**: Koin for clean dependency management
- **Navigation**: PreCompose for type-safe navigation

## 🎯 Core Features Implementation

### Navigation System
Type-safe navigation using PreCompose:
```kotlin
// Navigation Setup
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Favorites : Screen("favorites")
    data class MovieDetail(val movieId: Int) : Screen("movie/$movieId") {
        companion object {
            const val routePattern = "movie/{movieId}"
        }
    }
}

// Navigation Usage
NavHost(navigator = navigator, initialRoute = Screen.Home.route) {
    scene(route = Screen.Home.route) { HomeScreen() }
    scene(route = Screen.MovieDetail.routePattern) { backStackEntry ->
        val movieId: Int = backStackEntry.path<Int>("movieId") ?: 0
        MovieDetailScreen(movieId = movieId)
    }
}
```

### Image Loading with Kamel
Efficient async image loading with caching:
```kotlin
@Composable
fun KamelAsyncImage(
    url: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: @Composable BoxScope.(progress: Float) -> Unit = {
        CircularProgressIndicator(progress = { it })
    },
    error: @Composable BoxScope.(Throwable) -> Unit = {
        Text(text = "🎬", fontSize = 32.sp)
    }
) {
    KamelImage(
        resource = { asyncPainterResource(Url(url)) },
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        onLoading = placeholder,
        onFailure = error
    )
}
```

### State Management with PreCompose
Reactive state management using StateFlow:
```kotlin
class HomeViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovies().collect { movies ->
                _uiState.value = _uiState.value.copy(topRatedMovies = movies)
            }
        }
    }
}
```

### Search with Debouncing
Real-time search with optimized API calls:
```kotlin
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel : ViewModel() {
    private val searchQueryFlow = MutableStateFlow("")
    
    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500) // Wait 500ms after user stops typing
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository.searchMovies(query)
                }
                .collect { results ->
                    _uiState.value = _uiState.value.copy(searchResults = results)
                }
        }
    }
}
```

### Local Storage with Multiplatform Settings
Cross-platform local data persistence:
```kotlin
class FavoritesRepository {
    private val settings = Settings()
    private val json = Json { ignoreUnknownKeys = true }
    
    fun addToFavorites(movie: Movie) {
        val currentFavorites = getFavorites().toMutableList()
        if (!currentFavorites.any { it.id == movie.id }) {
            currentFavorites.add(movie)
            saveFavorites(currentFavorites)
        }
    }
    
    private fun saveFavorites(favorites: List<Movie>) {
        settings["favorites"] = json.encodeToString(favorites)
    }
}
```

## 🎨 UI Design & Theming

### Design System
The app features a modern dark theme with:
- **Gradient Backgrounds**: Smooth color transitions
- **Custom Color Palette**: Carefully chosen colors for optimal contrast
- **Typography System**: Consistent text styles across the app
- **Animation System**: Smooth transitions and micro-interactions

### Key UI Components

**MovieCard**: Displays movie information with poster, rating, and details
**SearchMovieItem**: Horizontal layout for search results
**Navigation**: Modern bottom navigation with smooth animations
**Image Loading**: Progressive loading with elegant placeholders

### Responsive Design
- Adaptive layouts for different screen sizes
- Optimized for both portrait and landscape orientations
- Platform-specific status bar styling

## 🧪 Testing

### Running Tests
```bash
# Shared module tests
./gradlew :composeApp:testDebugUnitTest

# Android tests
./gradlew :composeApp:connectedAndroidTest

# iOS tests (requires Xcode)
./gradlew :composeApp:iosX64Test
```

### Test Structure
- **Unit Tests**: Business logic and ViewModels
- **Integration Tests**: Repository and API interactions
- **UI Tests**: Screen composables and user interactions

## 🚦 Build & Deployment

### Build Configurations
```bash
# Debug builds
./gradlew assembleDebug

# Release builds
./gradlew assembleRelease

# iOS framework
./gradlew linkReleaseFrameworkIosArm64
```

### CI/CD
The project is ready for continuous integration with:
- GitHub Actions workflows
- Automated testing on multiple platforms
- Release artifact generation

## 📱 Screenshots

| Home Screen | Search | Movie Detail | Favorites |
|-------------|---------|--------------|-----------|
| Browse popular movies | Real-time search | Detailed information | Manage favorites |

## 🔮 Future Enhancements

- [ ] Offline mode support
- [ ] Movie trailers integration
- [ ] User reviews and ratings
- [ ] Watchlist functionality
- [ ] Genre-based filtering
- [ ] Social sharing features
- [ ] Push notifications
- [ ] Dark/Light theme toggle

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Contributing Guidelines
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Ensure all tests pass before submitting

## 📞 Support

- **Issues**: Report bugs or request features via [GitHub Issues](https://github.com/yourusername/movieapp-kmp/issues)
- **Discussions**: Join community discussions
- **Documentation**: Check the code documentation for implementation details

## 🙏 Acknowledgments

- [TMDB](https://www.themoviedb.org/) for providing the movie database API
- [JetBrains](https://www.jetbrains.com/) for Kotlin Multiplatform and Compose Multiplatform
- [PreCompose](https://github.com/Tlaster/PreCompose) for navigation and ViewModel support
- [Kamel](https://github.com/Kamel-Media/Kamel) for image loading
- [Koin](https://insert-koin.io/) for dependency injection

## 📊 Project Stats

- **Languages**: Kotlin (95%), Swift (5%)
- **Shared Code**: ~90% code sharing between platforms
- **Architecture**: MVVM with reactive state management
- **UI Framework**: 100% Compose Multiplatform

---

**Built with ❤️ using Kotlin Multiplatform**

For more details about specific implementations, please check the source code and inline documentation.
