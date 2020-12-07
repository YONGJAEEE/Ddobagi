package com.example.ddobagi3.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_modify.et_content
import kotlinx.android.synthetic.main.activity_modify.et_title

class ModifyActivity : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore
    var backKeyPressedTime : Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val documentId = intent.getStringExtra("documentId")
        val weather = intent.getStringExtra("weather")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val date = intent.getStringExtra("date")
        val location = intent.getStringExtra("location")

        firestore = FirebaseFirestore.getInstance()
        val ref = firestore.collection("USER")
            .document(MyApplication.prefs.getString("uid", "null"))
            .collection("diary")

        et_title.setText(title)
        et_content.setText(content!!.toString().replace("_nbsp_","\n"))

        btn_modify.setOnClickListener() {
            if (isInput()) {
                val modifyData = hashMapOf(
                    "documentId" to documentId,
                    "title" to et_title.text.toString(),
                    "date" to date,
                    "weather" to weather,
                    "location" to location,
                    "content" to et_content.text.toString().replace("\n", "_nbsp_")
                )

                ref.document(documentId!!).set(modifyData)
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

    fun isInput(): Boolean {
        if (et_title.text.toString().replace(" ", "") == "") {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (et_content.text.toString().replace(" ", "") == "") {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else return true
    }
}