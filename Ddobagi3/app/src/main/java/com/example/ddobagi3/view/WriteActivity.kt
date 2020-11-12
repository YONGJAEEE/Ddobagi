package com.example.ddobagi3.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_write.*
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class WriteActivity : AppCompatActivity() {
    var firestore : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        firestore = FirebaseFirestore.getInstance()
        val time: LocalDateTime = LocalDateTime.now()

        if (intent.hasExtra("addressName")){

        }

        btn_save.setOnClickListener(){

            val a = hashMapOf(
                "title" to "ㅁㄴㅇㅁㄴㅇ",
                "date" to "2020-11-13",
                "weather" to "비",
                "location" to "동탄대로 1길",
                "content" to "ㅁ나ㅓㅇㅁ나ㅓ유ㅏㅓㅁ뉴아ㅓ"
                )

            val ref = firestore?.collection("USER")
                ?.document(MyApplication.prefs.getString("uid","null"))
                ?.collection("diary")

            ref!!.document(time.toString()).set(a)
                .addOnSuccessListener {
                    Toast.makeText(this, "일기를 저장하는데 성공했어요.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

            }.addOnFailureListener{
                    Toast.makeText(this, "일기를 저장하는데 실패했어요.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}