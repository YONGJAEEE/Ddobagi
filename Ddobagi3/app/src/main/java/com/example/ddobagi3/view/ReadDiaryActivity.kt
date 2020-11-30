package com.example.ddobagi3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ddobagi3.R
import kotlinx.android.synthetic.main.activity_read_diary.*

class ReadDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_diary)

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")
        val location = intent.getStringExtra("location")
        val weather = intent.getStringExtra("weather")

        tv_content.text = content!!.replace("_nbsp_", "\n")
        tv_date.text = date
        tv_location.text = location
        tv_title.text = title
        tv_weather.text = weather
    }
}