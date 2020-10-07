package com.example.ddobagi.data

data class WeatherData (
    val header: Header,
    val body: Body
)

data class Body (
    val dataType: String,
    val items: Items,
    val pageNo: Long,
    val numOfRows: Long,
    val totalCount: Long
)

data class Items (
    val item: List<Item>
)

data class Item (
    val announceTime: Long,
    val numEf: Long,
    val regID: String,
    val rnSt: Int,
    val rnYn: Int,
    val ta: Int,
    val wd1: String,
    val wd2: String,
    val wdTnd: String,
    val wf: String,
    val wfCD: String,
    val wsIt: String
)

data class Header (
    val resultCode: String,
    val resultMsg: String
)