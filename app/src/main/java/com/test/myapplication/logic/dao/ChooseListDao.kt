package com.test.myapplication.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.test.myapplication.logic.model.chooseList
import com.test.myapplication.logic.model.myTextMap
import com.test.myapplication.ui.MyApplication

object ChooseListDao {
    fun saveList() {
        sharedPreferences().edit {
            putString("chooseID", Gson().toJson(chooseList))
        }
    }
    fun getSaveList():List<String>{
            val savedJson=sharedPreferences().getString("chooseID","")
            return Gson().fromJson(savedJson, chooseList::class.java)
        }
        fun isListSaved()=sharedPreferences().contains("chooseID")
    fun saveMap() {
        sharedPreferences().edit {
            putString("myTextMap", Gson().toJson(myTextMap))
        }
    }
    fun getSaveMap():Map<String,String>{
        val savedJson=sharedPreferences().getString("myTextMap","")
        return Gson().fromJson(savedJson, myTextMap::class.java)
    }
    fun isMapSaved()=sharedPreferences().contains("myTextMap")

    private fun sharedPreferences()=MyApplication.context.getSharedPreferences("save", Context.MODE_PRIVATE)
}