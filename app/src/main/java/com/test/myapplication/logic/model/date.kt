package com.test.myapplication.logic.model

import com.google.gson.annotations.SerializedName

data class date(val page:String,val rows:List<Rows>) {
    data class Rows(val id:String,val cell:Cell)
    data class Cell(@SerializedName("fund_nm") val name:String,val price:Float,@SerializedName("discount_rt") var coverprice:String
    ,@SerializedName("apply_status")val sg:String,@SerializedName("redeem_status") val sh:String)
}