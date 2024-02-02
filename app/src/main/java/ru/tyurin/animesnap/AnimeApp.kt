package ru.tyurin.animesnap

import android.app.Application
import ru.tyurin.animesnap.data.AppContainer
import ru.tyurin.animesnap.data.DefaultAppContainer

class AnimeApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}