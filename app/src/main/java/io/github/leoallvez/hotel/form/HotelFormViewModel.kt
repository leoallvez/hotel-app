package io.github.leoallvez.hotel.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.leoallvez.hotel.model.Hotel
import io.github.leoallvez.hotel.repository.HotelRepository

class HotelFormViewModel(private val repository: HotelRepository) : ViewModel() {

    private val validator by lazy { HotelValidator() }

    fun loadHotel(id: Long): LiveData<Hotel> {
        return repository.hotelById(id)
    }

    fun saveHotel(hotel: Hotel): Boolean {
        return validator.validate(hotel).also { validated ->
            if(validated) repository.save(hotel)
        }
    }
}
