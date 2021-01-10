package com.example.ddobagi3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ddobagi3.R
import com.example.ddobagi3.databinding.ActivityMainBinding
import com.example.ddobagi3.databinding.ActivityReadDiaryBinding
import com.example.ddobagi3.model.DocumentId
import com.example.ddobagi3.viewmodel.MainViewModel
import com.example.ddobagi3.viewmodel.ReadDiaryViewModel
import com.example.ddobagi3.widget.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_read_diary.*

class ReadDiaryActivity : AppCompatActivity() {
    lateinit var binding : ActivityReadDiaryBinding
    lateinit var viewModel : ReadDiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

        val ref = firestore.collection("USER")
            .document(MyApplication.prefs.getString("uid","null"))
            .collection("diary")
            .document(DocumentId.value)

        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Log.d("TAG", "Current data: ${snapshot.data}")
                tv_content.text = snapshot.data!!["content"]!!.toString().replace("_nbsp_", "\n")
                tv_date.text = snapshot.data!!["date"]!!.toString()
                tv_location.text = snapshot.data!!["location"].toString()
                tv_title.text = snapshot.data!!["title"].toString()
                tv_weather.text = snapshot.data!!["weather"].toString()
            } else {
                Log.e("TAG", "Current data: null")
            }
        }
    }

    private fun init(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_read_diary)
        viewModel = ViewModelProvider(this)[ReadDiaryViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}