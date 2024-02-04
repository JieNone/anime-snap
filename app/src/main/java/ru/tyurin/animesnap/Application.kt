package ru.tyurin.animesnap

import android.app.Application
import dagger.Component
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application() {
    lateinit var component: Component


    override fun onCreate() {
        super.onCreate()
        component = Component()
    }
}
