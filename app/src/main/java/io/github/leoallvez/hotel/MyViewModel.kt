package io.github.leoallvez.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val count = MutableLiveData<Int>()

    fun getCount(): LiveData<Int> = count

    fun setCount(value: Int) {
        count.value = value
    }
}