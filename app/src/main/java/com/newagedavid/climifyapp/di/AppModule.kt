package com.newagedavid.climifyapp.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.newagedavid.climifyapp.data.remote.OpenWeatherApi
import com.newagedavid.climifyapp.data.repository.CityRepository
import com.newagedavid.climifyapp.data.repository.CityRepositoryImpl
import com.newagedavid.climifyapp.data.repository.DailyForecastRepository
import com.newagedavid.climifyapp.data.repository.HourlyCityForecastRepository
import com.newagedavid.climifyapp.data.repository.WeatherRepository
import com.newagedavid.climifyapp.domain.usecase.GetForecastUseCase
import com.newagedavid.climifyapp.domain.usecase.GetHourlyForecastUseCase
import com.newagedavid.climifyapp.domain.usecase.GetNextFourDaysUseCase
import com.newagedavid.climifyapp.domain.usecase.GetWeatherUseCase
import com.newagedavid.climifyapp.domain.usecase.RefreshCityWeatherUseCase
import com.newagedavid.climifyapp.domain.usecase.SaveDailyForecastsUseCase
import com.newagedavid.climifyapp.domain.usecase.SaveHourlyForecastsUseCase
import com.newagedavid.climifyapp.domain.usecase.city.AddCityUseCase
import com.newagedavid.climifyapp.domain.usecase.city.GetCitiesWithCurrentWeatherUseCase
import com.newagedavid.climifyapp.ui.city.CityManagerViewModel
import com.newagedavid.climifyapp.ui.details.DetailsViewModel
import com.newagedavid.climifyapp.ui.home.HomeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appModule = module {
    single { providePreferences(get()) }
    single { provideMoshi() }
    single { provideOkHttp() }
    single { provideRetrofit(get(), get()) }
    single { provideOpenWeatherApi(get()) }

    //repos
    single { WeatherRepository(get()) }
    single { DailyForecastRepository(get()) }
    single { HourlyCityForecastRepository(get()) }
    single <CityRepository> { CityRepositoryImpl(get(), get())}

    //use cases
    single { GetWeatherUseCase(get()) }
    single { GetForecastUseCase(get()) }
    single { SaveHourlyForecastsUseCase(get()) }
    single { SaveDailyForecastsUseCase(get()) }
    single { AddCityUseCase(get(), get()) }
    single { GetCitiesWithCurrentWeatherUseCase(get()) }
    single { RefreshCityWeatherUseCase(get(), get(), get(), get()) }
    single { GetHourlyForecastUseCase(get()) }
    single { GetNextFourDaysUseCase(get()) }

    //view models
    viewModel { HomeViewModel(get(), get(), get(), get())}
    viewModel { CityManagerViewModel(get(), get())}
    viewModel { DetailsViewModel() }
}

fun providePreferences(context: Context): SharedPreferences =
    context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun provideOkHttp(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi =
    retrofit.create(OpenWeatherApi::class.java)
