package com.example.ddobagi3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ddobagi3.R
import com.example.ddobagi3.adpater.AdressAdapter
import com.example.ddobagi3.databinding.ActivityJusoBinding
import com.example.ddobagi3.viewmodel.JusoViewModel
import com.example.ddobagi3.widget.TextObserver
import kotlinx.android.synthetic.main.activity_juso.*

class JusoActivity : AppCompatActivity() {
    lateinit var binding : ActivityJusoBinding
    lateinit var viewModel : JusoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        et_adress.addTextChangedListener(TextObserver(viewModel,et_adress))
        with(viewModel){
            jusoList.observe(this@JusoActivity, Observer {
                rv_adress.adapter = AdressAdapter(jusoList.value!!)
            })
        }
    }

    private fun init(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_juso)
        viewModel = ViewModelProvider(this)[JusoViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}