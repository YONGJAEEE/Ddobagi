package com.example.ddobagi3.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.adpater.DiaryAdapter
import com.example.ddobagi3.model.DiaryData
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private var buttonIcon = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        val formatted = current.format(formatter)
        TodayDate.text = formatted

        val diaryList = arrayListOf(
            DiaryData("히", "2020-10-10", "경기 1로 9", "1"),
            DiaryData("해위", "2020-10-10", "동탄 1로 9", "2"),
            DiaryData("버억", "2020-10-10", "꽈당 1로 9", "3"),
            DiaryData("아무", "2020-10-10", "서울 1로 9", "4"),
            DiaryData("아무개", "2020-10-10", "의왕 1로 9", "5"),
            DiaryData("마마무", "2020-10-10", "과천 1로 9", "6"),
            DiaryData("이나은", "2020-10-10", "사당 1로 9", "7")
            )
        btn_float.setButtonIconResource(R.drawable.ic_open)
        btn_float.speedDialMenuAdapter = speedDialMenuAdapter
        recyclerview.adapter = DiaryAdapter(diaryList)
    }

    private val speedDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount() = 2

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem = when (position){
            0 -> SpeedDialMenuItem(context, R.drawable.write,"Write Diary")
            1 -> SpeedDialMenuItem(context, R.drawable.ic_aboutme,"About Developer")
            else -> throw IllegalArgumentException("No Menu Item")
        }
        override fun fabRotationDegrees(): Float = if (buttonIcon == 0) 135F else 0F

        override fun onMenuItemClick(position: Int): Boolean {
            when(position){
                0 -> startActivity(Intent(this@MainActivity,JusoActivity::class.java))
                1 -> startActivity(Intent(this@MainActivity,AboutMeActivity::class.java))
            }
            return true
        }
    }
}