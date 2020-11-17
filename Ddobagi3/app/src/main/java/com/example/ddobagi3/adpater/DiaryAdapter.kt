package com.example.ddobagi3.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item.view.*

class DiaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var diaryList : ArrayList<DiaryData> = arrayListOf()
    var firestore : FirebaseFirestore? = null
    init {
        firestore = FirebaseFirestore.getInstance()

        val ref = firestore?.collection("USER")
            ?.document(MyApplication.prefs.getString("uid","null"))
            ?.collection("diary")!!.orderBy("date", Query.Direction.DESCENDING)

        ref.addSnapshotListener() {querySnapshot, _->
            diaryList.clear()
            for (snapshot in querySnapshot!!.documents) {
                val item = DiaryData(
                    snapshot.get("title").toString(),
                    snapshot.get("date").toString(),
                    snapshot.get("weather").toString(),
                    snapshot.get("location").toString(),
                    snapshot.get("content").toString()
                )
                diaryList.add(item)
            }
            notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = (holder as ViewHolder).itemView

        viewHolder.tv_title.text = diaryList[position].title
        viewHolder.tv_date.text = diaryList[position].date
        viewHolder.tv_adress.text = diaryList[position].location
    }

    // 리사이클러뷰의 아이템 총 개수 반환
    override fun getItemCount(): Int {
        return diaryList.size
    }
}