package com.newagedavid.climifyapp.di

import androidx.room.Room
import com.newagedavid.climifyapp.data.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "climify_db"
        ).build()
    }

    single { get<AppDatabase>().dailyCityForecastDao()}
    single { get<AppDatabase>().hourlyForecastDao()}
    single { get<AppDatabase>().cityDao()}
}
