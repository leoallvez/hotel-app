package io.github.leoallvez.hotel

import android.app.Application
import io.github.leoallvez.hotel.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

//import org.koin.android.ext.android.startKoin
//import org.koin.standalone.StandAloneContext.stopKoin

class HotelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //startKoin(this, listOf(androidModule))

        //Start Koin
        startKoin {
            androidLogger()
            androidContext(this@HotelApp)
            modules(androidModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}