package io.github.leoallvez.hotel.common

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.leoallvez.hotel.*
import io.github.leoallvez.hotel.details.HotelDetailsActivity
import io.github.leoallvez.hotel.details.HotelDetailsFragment
import io.github.leoallvez.hotel.form.HotelFormFragment
import io.github.leoallvez.hotel.list.HotelListFragment
import io.github.leoallvez.hotel.model.Hotel

class HotelActivity : AppCompatActivity(),
        HotelListFragment.OnHotelClickListener,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener,
        HotelFormFragment.OnHotelSavedListener,
        HotelListFragment.OnHotelDeletedListener {

    private var hotelIdSelected: Long = -1

    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null
    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            listFragment.search(lastSearchTerm)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putLong(EXTRA_HOTEL_ID_SELECTED, hotelIdSelected)
        outState.putString(EXTRA_SEARCH_TERM, lastSearchTerm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        hotelIdSelected = savedInstanceState?.getLong(EXTRA_HOTEL_ID_SELECTED) ?: 0
        lastSearchTerm = savedInstanceState?.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    override fun onHotelClick(hotel: Hotel) = if(isTablet()) {
        hotelIdSelected = hotel.id
        showDetailsFragment(hotel.id)
    } else {
        showDetailsActivity(hotel.id)
    }

    private fun showDetailsFragment(hotelId: Long) {
        searchView?.setOnQueryTextListener(null)
        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if(lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_info ->
                AboutDialogFragment().show(supportFragmentManager, "sobre")
            R.id.action_new ->
                HotelFormFragment.newInstance().open(supportFragmentManager)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onMenuItemActionExpand(item: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch()
        return true
    }

    private fun isTablet() = findViewById<View>(R.id.details) != null

    override fun onHotelSaved(hotel: Hotel) {
        listFragment.search(lastSearchTerm)
        val detailsFragment = supportFragmentManager
                .findFragmentByTag(HotelDetailsFragment.TAG_DETAILS) as? HotelDetailsFragment
        if(detailsFragment != null && hotel.id == hotelIdSelected) {
            showDetailsFragment(hotelIdSelected)
        }
    }

    override fun onHotelsDeleted(hotels: List<Hotel>) {
        if(hotels.find{ it.id == hotelIdSelected} != null) {
            val fragment = supportFragmentManager.findFragmentByTag(HotelDetailsFragment.TAG_DETAILS)
            fragment?.let {
                supportFragmentManager
                        .beginTransaction()
                        .remove(fragment)
                        .commit()
            }
        }
    }

    companion object {
        const val EXTRA_SEARCH_TERM  = "lastSearch"
        const val EXTRA_HOTEL_ID_SELECTED = "lastSelectedId"
    }
}