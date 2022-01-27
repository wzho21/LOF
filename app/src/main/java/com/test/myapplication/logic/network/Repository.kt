package com.test.myapplication.logic.network

import androidx.lifecycle.liveData
import com.test.myapplication.logic.dao.ChooseListDao
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    fun saveList()=ChooseListDao.saveList()
    fun saveMap() = ChooseListDao.saveMap()
    fun getSavedMap()=ChooseListDao.getSaveMap()
    fun isSaved()=ChooseListDao.isListSaved()
    fun getSaveList()=ChooseListDao.getSaveList()
    fun isMapSaved()=ChooseListDao.isMapSaved()
    private fun<T>fire(context:CoroutineContext,block:suspend ()->Result<T>)= liveData<Result<T>>(context){
        val result=try {
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }
    fun getjjLof(string: String)= fire(Dispatchers.IO){
        val jjResponse=dateNetWork.getLOFjj()
        if (jjResponse.page=="1"){
            val response=jjResponse.rows
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response is null"))
        }
    }
    fun getgpLof(string: String)= fire(Dispatchers.IO){
        val gpResponse=dateNetWork.getLOFgp()
        if (gpResponse.page=="1"){
            val response=gpResponse.rows
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response is null"))
        }
    }

}