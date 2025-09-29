# 🌤️ ClimifyApp

ClimifyApp is a modern weather application built with **Jetpack Compose**.  
It allows users to search, add, and manage their favorite cities, and view real-time weather updates, hourly forecasts, and daily forecasts.

## ✨ Features

- 📍 **City Manager** – Add, edit, and remove your favorite cities
- 🏙️ **Home Screen Pager** – Swipe between saved cities with a smooth pager
- ☀️ **Dynamic Backgrounds** – Weather-based background images (day/night aware)
- 🌡️ **Current Weather** – Temperature, conditions, and icons per city
- ⏳ **Hourly Forecasts** – Horizontal scroll with upcoming weather
- 📅 **Daily Forecasts** – 7-day forecast list view
- 🗂️ **Offline Storage** – Room database keeps forecasts cached locally
- 🚀 **Clean Architecture** – Repository + UseCase + ViewModel separation  


## 🛠️ Tech Stack

- 🟨 **Kotlin** + **Coroutines** + **Flow**
- 🎨 **Jetpack Compose** (UI)
- 🧭 **Navigation Compose** (multi-screen navigation)
- 🗄️ **Room** (local database for weather + forecasts)
- 🪢 **Koin** (dependency injection)
- 🎛️ **Material3** (UI components)
- 🏗️ **MVVM + Clean Architecture** (ViewModel, Repository, UseCases)  


## ⚙️ Configuration

The app currently fetches weather data (replace with your chosen API, e.g., **OpenWeatherMap**).

Add your API key in `local.properties` or inside a `Secrets.kt` file:

```kotlin
const val WEATHER_API_KEY = "your_api_key_here"
