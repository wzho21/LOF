package com.test.myapplication.logic.network

import com.test.myapplication.logic.model.date
import retrofit2.Call
import retrofit2.http.GET

interface dateService2 {
    @GET("index_lof_list/?___jsl=LST___t=1611402086620&rp=25&page=1")
    fun getLOFjj():Call<date>
}