package io.github.leoallvez.hotel.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.leoallvez.hotel.model.Hotel
import io.github.leoallvez.hotel.repository.sqlite.COLUMN_ID
import io.github.leoallvez.hotel.repository.sqlite.COLUMN_NAME
import io.github.leoallvez.hotel.repository.sqlite.TABLE_HOTEL

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hotel: Hotel): Long

    @Update
    fun update(hotel: Hotel): Int

    @Delete
    fun delete(vararg hotel: Hotel): Int

    @Query("SELECT * FROM $TABLE_HOTEL WHERE $COLUMN_ID = :id")
    fun hotelById(id: Long): LiveData<Hotel>

    @Query("""
        SELECT * FROM $TABLE_HOTEL 
        WHERE $COLUMN_NAME LIKE :query ORDER BY $COLUMN_NAME""")
    fun search(query: String): LiveData<List<Hotel>>
}