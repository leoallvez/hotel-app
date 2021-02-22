package io.github.leoallvez.hotel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.leoallvez.hotel.model.Hotel
import io.github.leoallvez.hotel.repository.HotelRepository

class HotelDetailsViewModel(private val repository: HotelRepository) : ViewModel() {

    fun loadHotelDetails(id: Long): LiveData<Hotel> {
        return repository.hotelById(id)
    }
}
