package com.example.ddobagi3.model

import java.io.Serializable

class DiaryData (
    var documentId : String,
    var title : String,
    var date : String,
    var weather : String,
    var location : String,
    var content : String
): Serializable

