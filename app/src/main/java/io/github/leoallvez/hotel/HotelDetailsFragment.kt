package io.github.leoallvez.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_hotel_details.*

class HotelDetailsFragment: Fragment(), HotelDetailsView {

    private val presenter = HotelDetailsPresenter(this, MemoryRepository)
    private var hotel: Hotel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadHotelDetails(arguments?.getLong(EXTRA_HOTEL_ID, -1) ?: 1)
    }

    override fun showHotelDetails(hotel: Hotel) {
        this.hotel = hotel
        txt_name.text = hotel.name
        txt_address.text = hotel.adress
        rtb_rating.rating = hotel.rating
    }

    override fun errorHotelNotFound() {
        txt_name.text = getString(R.string.error_hotel_not_found)
        txt_address.visibility = View.GONE
        rtb_rating.visibility = View.GONE
    }

    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_HOTEL_ID = "hotelId"

        fun newInstance(id: Long) = HotelDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, id)
            }
        }
    }
}