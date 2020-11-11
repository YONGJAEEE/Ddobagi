package com.example.ddobagi3.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item.view.*

class DiaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var diaryList = mutableListOf<DiaryData>()
    var firestore : FirebaseFirestore? = null

    init {
        firestore = FirebaseFirestore.getInstance()

        val ref = firestore?.collection("USER")
            ?.document(MyApplication.prefs.getString("uid","null"))
            ?.collection("diary")

        ref!!.get().addOnSuccessListener {result->
            diaryList.clear()
            for (document in result) {
                val diaryData = DiaryData(
                    document.data["title"].toString(),
                    document.data["date"].toString(),
                    document.data["hashtag"].toString(),
                    document.data["weather"].toString(),
                    document.data["location"].toString(),
                    document.data["content"].toString()
                )
                Log.d("TAG", "${document.id} => ${document.data}")
                diaryList.add(diaryData)
            }
            notifyDataSetChanged()

        }.addOnFailureListener {
            Log.e("test", "fAIL")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as ViewHolder).itemView

        viewHolder.tv_title.text = diaryList[position].title
        viewHolder.tv_date.text = diaryList[position].date
        viewHolder.tv_adress.text = diaryList[position].location
    }

    // 리사이클러뷰의 아이템 총 개수 반환
    override fun getItemCount(): Int {
        return diaryList.size
    }
}