package com.test.myapplication.ui.jj

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.test.myapplication.logic.model.date
import com.test.myapplication.logic.network.Repository

class jjViewModel:ViewModel() {
    private val dateLiveData=MutableLiveData<String>()
    val dateList=ArrayList<date.Rows>()
    val lofLiveDate=Transformations.switchMap(dateLiveData){string->
    Repository.getjjLof(string)}
    fun refreshdate(string: String){
        dateLiveData.value=string
    }
}