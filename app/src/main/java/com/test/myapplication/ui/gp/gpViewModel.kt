package com.test.myapplication.ui.gp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.test.myapplication.logic.model.date
import com.test.myapplication.logic.network.Repository

class gpViewModel:ViewModel() {
    fun saveList()=Repository.saveList()
    fun getSavedList()=Repository.getSaveList()
    fun isSaved()=Repository.isSaved()
    fun saveMap()=Repository.saveMap()
    fun getSavedMap()=Repository.getSavedMap()
    fun isMapSaved()=Repository.isMapSaved()
    private val dateLiveData=MutableLiveData<String>()
    val dateList=ArrayList<date.Rows>()
    val lofLiveDate=Transformations.switchMap(dateLiveData){string->
    Repository.getgpLof(string)}
    fun refreshdate(string: String){
        dateLiveData.value=string
    }
}