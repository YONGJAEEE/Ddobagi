package com.example.ddobagi3.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.DiaryData
import com.google.firebase.firestore.FirebaseFirestore

class DiaryAdapter(firestore : FirebaseFirestore?) : RecyclerView.Adapter<DiaryAdapter.Holder>(){
    var diaryList : ArrayList<DiaryData> = arrayListOf()
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
        val adress = itemView.findViewById<TextView>(R.id.tv_adress)


        fun bind(diary: DiaryData) {
            Log.d("TAG", diary.toString())
            title.text = diary.title
            date.text = diary.date
            adress.text = diary.location
        }
    }
    init {
        firestore?.collection("book")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            diaryList.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(DiaryData::class.java)
                diaryList.add(item!!)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(diaryList[position])
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }
}