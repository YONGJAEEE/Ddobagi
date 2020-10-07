package com.example.ddobagi.data

data class AreaResponse (
    val common: Common?,
    val juso: List<Juso>?
)

data class Common (
    val errorMessage: String?,
    val countPerPage: String?,
    val totalCount: String?,
    val errorCode: String?,
    val currentPage: String?
)

data class Juso (

    val roadAddrPart1: String?,
)