package io.github.leoallvez.hotel.di

import io.github.leoallvez.hotel.details.HotelDetailsPresenter
import io.github.leoallvez.hotel.details.HotelDetailsView
import io.github.leoallvez.hotel.form.HotelFormPresenter
import io.github.leoallvez.hotel.form.HotelFormView
import io.github.leoallvez.hotel.list.HotelListPresenter
import io.github.leoallvez.hotel.list.HotelListView
import io.github.leoallvez.hotel.repository.HotelRepository
import io.github.leoallvez.hotel.repository.sqlite.SQLiteRepository
import org.koin.dsl.module

//import org.koin.dsl.module.module

val androidModule = module {
    single { this }
    single {
        SQLiteRepository(context = get()) as HotelRepository
    }
    factory { (view: HotelListView) ->
        HotelListPresenter(
            view,
            repository = get()
        )
    }
    factory { (view: HotelDetailsView) ->
        HotelDetailsPresenter(
            view,
            repository = get()
        )
    }
    factory { (view: HotelFormView) ->
        HotelFormPresenter(
            view,
            repository = get()
        )
    }
}