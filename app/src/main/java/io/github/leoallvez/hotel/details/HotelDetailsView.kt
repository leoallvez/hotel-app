package io.github.leoallvez.hotel.details

import io.github.leoallvez.hotel.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}