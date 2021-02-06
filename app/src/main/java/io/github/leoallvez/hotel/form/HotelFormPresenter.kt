package io.github.leoallvez.hotel.form

import io.github.leoallvez.hotel.model.Hotel
import io.github.leoallvez.hotel.repository.HotelRepository
import java.lang.Exception

class HotelFormPresenter (
        private val view: HotelFormView,
        private val repository: HotelRepository)
{
    private val validator = HotelValidator()

    fun loadHotel(id: Long) {
        repository.hotelById(id) { hotel ->
            if(hotel != null) {
                view.showHotel(hotel)
            }
        }
    }

    fun saveHotel(hotel: Hotel) = if(validator.validate(hotel)) {
        try {
            repository.save(hotel)
            true
        } catch (e: Exception) {
            view.errorSaveHotel()
            false
        }
    } else {
        view.errorInvalidHotel()
        false
    }

}