package io.github.leoallvez.hotel

import android.app.Application
import io.github.leoallvez.hotel.di.androidModule
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class HotelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(androidModule))
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}