package com.example.ddobagi3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ddobagi3.R
import kotlinx.android.synthetic.main.activity_read_diary.*

class ReadDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_diary)

        tv_content.text = "asdadssdaasasdads\nsadadsadsasd"
        tv_date.text = "2020-11-11"
        tv_location.text = "동탄대로 1길"
        tv_title.text = "제목이지롱"
    }
}