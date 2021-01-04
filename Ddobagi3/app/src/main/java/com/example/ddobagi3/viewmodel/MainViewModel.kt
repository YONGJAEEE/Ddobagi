package com.example.ddobagi3.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    var diaryList = MutableLiveData<ArrayList<DiaryData>>()
    var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val ref = firestore.collection("USER")
        .document(MyApplication.prefs.getString("uid","null"))
        .collection("diary")
    init {
        Log.d("TAGTAG","ㅎㅇ")
        getDiary()
    }

    private fun getDiary(){
        ref.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener() { querySnapshot, _ ->
            val tempList = ArrayList<DiaryData>()
            for (snapshot in querySnapshot!!.documents) {
                val item = DiaryData(
                    snapshot.get("documentId").toString(),
                    snapshot.get("title").toString(),
                    snapshot.get("date").toString(),
                    snapshot.get("weather").toString(),
                    snapshot.get("location").toString(),
                    snapshot.get("content").toString()
                )
                tempList.add(item)
            }
            diaryList.value = tempList
        }
    }
}