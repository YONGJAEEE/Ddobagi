package com.example.ddobagi3.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.model.DocumentId
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_modify.et_content
import kotlinx.android.synthetic.main.activity_modify.et_title
import kotlinx.android.synthetic.main.activity_modify.tv_weather

class ModifyActivity : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore
    var backKeyPressedTime: Long = 0
    lateinit var location: String
    lateinit var date: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        firestore = FirebaseFirestore.getInstance()
        val ref = firestore.collection("USER")
            .document(MyApplication.prefs.getString("uid", "null"))
            .collection("diary")
            .document(DocumentId.value)

        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                date = snapshot.data!!["date"].toString()
                location = snapshot.data!!["location"].toString()
                et_title.setText(snapshot.data!!["title"].toString())
                et_content.setText(snapshot.data!!["content"].toString().replace("_nbsp_", "\n"))
                tv_weather.text = snapshot.data!!["weather"].toString()
            } else {
                Log.e("TAG", "Current data: null")
            }
        }

        btn_modify.setOnClickListener() {
            if (isInput()) {
                val modifyData = hashMapOf(
                    "documentId" to DocumentId.value,
                    "title" to et_title.text.toString(),
                    "date" to date,
                    "weather" to tv_weather.text.toString(),
                    "location" to location,
                    "content" to et_content.text.toString().replace("\n", "_nbsp_")
                )

                ref.set(modifyData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "수정에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener() {
                        Toast.makeText(this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 \n 일기 수정이 취소됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }

    private fun isInput(): Boolean {
        return when {
            et_title.text.toString().replace(" ", "") == "" -> {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                false
            }
            et_content.text.toString().replace(" ", "") == "" -> {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}