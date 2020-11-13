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
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class WriteActivity : AppCompatActivity() {
    var firestore : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        firestore = FirebaseFirestore.getInstance()
        val time: LocalDateTime = LocalDateTime.now()

        val adressName = intent.getStringExtra("adressName")
        val x = intent.getStringExtra("x")
        val y = intent.getStringExtra("y")

        btn_save.setOnClickListener(){

            val a = hashMapOf(
                "title" to et_title.text.toString(),
                "date" to LocalDate.now().toString(),
                "weather" to "비",
                "location" to adressName,
                "content" to et_content.text.toString()
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