package io.github.leoallvez.hotel.di

import io.github.leoallvez.hotel.details.HotelDetailsViewModel
import io.github.leoallvez.hotel.form.HotelFormViewModel
import io.github.leoallvez.hotel.list.HotelListViewModel
import io.github.leoallvez.hotel.repository.HotelRepository
import io.github.leoallvez.hotel.repository.room.HotelDatabase
import io.github.leoallvez.hotel.repository.room.RoomRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
//import io.github.leoallvez.hotel.repository.HotelRepository
//import io.github.leoallvez.hotel.repository.sqlite.ProviderRepository
import org.koin.dsl.module.module

val androidModule = module {
    single { this }
    single {
        RoomRepository(HotelDatabase.getDatabase(context = get())) as HotelRepository
    }
    viewModel {
        HotelListViewModel(repository = get())
    }
    viewModel {
        HotelDetailsViewModel(repository = get())
    }
    viewModel {
        HotelFormViewModel(repository = get())
    }
}