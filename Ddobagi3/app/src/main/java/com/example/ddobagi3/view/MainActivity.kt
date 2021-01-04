package com.example.ddobagi3.view
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ddobagi3.R
import com.example.ddobagi3.adpater.DiaryAdapter
import com.example.ddobagi3.adpater.FabAdapter
import com.example.ddobagi3.databinding.ActivityMainBinding
import com.example.ddobagi3.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        btn_float.setButtonIconResource(R.drawable.ic_open)
        btn_float.speedDialMenuAdapter = FabAdapter(this)

        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        val todayDate = LocalDateTime.now().format(formatter)
        TodayDate.text = todayDate

        with(viewModel) {
            diaryList.observe(this@MainActivity, androidx.lifecycle.Observer {
                val diarytAdapter = DiaryAdapter(diaryList.value!!)
                recyclerview.adapter = diarytAdapter
            })
        }
    }

    private fun init(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 \n 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }
}