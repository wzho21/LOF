package com.test.myapplication.logic.network

import kotlinx.coroutines.flow.combineTransform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object dateNetWork {
    private val service1=ServiceCreator.create(dateService::class.java)
    private val service2=ServiceCreator.create(dateService2::class.java)
    suspend fun getLOFgp()= service1.getLOFgp().await()
    suspend fun getLOFjj()= service2.getLOFjj().await()
    private suspend fun <T>Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if(body!=null)continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}