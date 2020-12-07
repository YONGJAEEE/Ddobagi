package com.example.ddobagi3.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.model.WeatherResponse
import com.example.ddobagi3.network.WeatherClient
import com.example.ddobagi3.widget.MyApplication
import com.example.ddobagi3.widget.Translation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_write.*
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class WriteActivity : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore
    var todayWeather = ""
    val translation = Translation()
    var backKeyPressedTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        firestore = FirebaseFirestore.getInstance()
        val time: LocalDateTime = LocalDateTime.now()

        val adressName = intent.getStringExtra("adressName")
        val lon = intent.getStringExtra("x")
        val lat = intent.getStringExtra("y")
        getWeather(lat!!, lon!!)

        btn_save.setOnClickListener() {
            val strTime = time.toString()
            if (isInput()) {
                val sendData = hashMapOf(
                    "documentId" to strTime,
                    "title" to et_title.text.toString(),
                    "date" to LocalDate.now().toString(),
                    "weather" to todayWeather,
                    "location" to adressName,
                    "content" to et_content.text.toString().replace("\n", "_nbsp_")
                )

                val ref = firestore.collection("USER")
                    .document(MyApplication.prefs.getString("uid", "null"))
                    .collection("diary")

                ref.document(strTime).set(sendData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "일기를 저장하는데 성공했어요.", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "일기를 저장하는데 실패했어요.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 \n 일기 작성이 취소됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }

    fun getWeather(x: String, y: String) {
        val call: Call<WeatherResponse> =
            WeatherClient.instance.GetData.getWeather(x, y, "117f9473192d7aaf0fb9843665d8eb99")

        call.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Log.d("TAG", response.body().toString())
                todayWeather = translation.changeWeather(response.body()!!.weather!![0].id!!)
                tv_weather.setText(todayWeather)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("Fail", t.toString())
            }
        }
        )
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