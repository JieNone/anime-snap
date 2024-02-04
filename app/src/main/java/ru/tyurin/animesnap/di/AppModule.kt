package ru.tyurin.animesnap.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.tyurin.animesnap.data.utils.SharedPreferencesKeys
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {


    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPreferencesKeys.APP_PREFS, Context.MODE_PRIVATE)
    }
}