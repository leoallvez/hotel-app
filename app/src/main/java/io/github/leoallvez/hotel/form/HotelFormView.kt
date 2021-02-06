package io.github.leoallvez.hotel.form

import io.github.leoallvez.hotel.model.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
}