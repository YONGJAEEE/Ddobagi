package com.example.ddobagi3.adpater

import android.app.AlertDialog
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.view.MainActivity
import com.example.ddobagi3.view.ModifyActivity
import com.example.ddobagi3.view.ReadDiaryActivity
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore

class DiaryAdapter(var diaryList: ArrayList<DiaryData>) :
    RecyclerView.Adapter<DiaryAdapter.Holder>() {

    var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        val tvLocation = itemView.findViewById<TextView>(R.id.tv_adress)
        val btnModify = itemView.findViewById<ImageButton>(R.id.btn_modify)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)

        fun bind(diaryData: DiaryData) {
            tvTitle.text = diaryData.title
            tvDate.text = diaryData.date
            tvLocation.text = diaryData.location

            btnModify.setOnClickListener() {
                val intent = Intent(itemView.context, ModifyActivity::class.java)
                intent.putExtra("documentId", diaryData.documentId)
                intent.putExtra("weather", diaryData.weather)
                intent.putExtra("title", diaryData.title)
                intent.putExtra("content", diaryData.content)
                intent.putExtra("location",diaryData.location)
                intent.putExtra("date",diaryData.date)
                itemView.context.startActivity(intent)
            }
            btnDelete.setOnClickListener() {
                val builder = AlertDialog.Builder(
                    ContextThemeWrapper(
                        itemView.context,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
                builder.setTitle("다이어리 삭제")
                builder.setMessage("정말 이 다이어리를 삭제하시겠습니까?")
                builder.setPositiveButton("확인") { _, _ ->
                    val ref = firestore.collection("USER")
                        .document(MyApplication.prefs.getString("uid", "null"))
                        .collection("diary")

                    ref.document(diaryData.documentId).delete()
                        .addOnSuccessListener {
                            Toast.makeText(itemView.context, "삭제에 성공했습니다.", Toast.LENGTH_SHORT)
                                .show()
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                            Toast.makeText(itemView.context, "삭제에 실패했습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                builder.setNegativeButton("취소"){_,_->
                    Toast.makeText(itemView.context, "삭제가 취소되었습니다.", Toast.LENGTH_SHORT).show()
                }
                builder.show()

            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ReadDiaryActivity::class.java)
                intent.putExtra("documentId", diaryData.documentId)
                intent.putExtra("title", diaryData.title)
                intent.putExtra("date", diaryData.date)
                intent.putExtra("content", diaryData.content)
                intent.putExtra("location", diaryData.location)
                intent.putExtra("weather", diaryData.weather)
                itemView.context.startActivity(intent)
            }
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