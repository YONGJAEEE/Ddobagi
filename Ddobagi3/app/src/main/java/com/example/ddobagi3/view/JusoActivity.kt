package com.example.ddobagi3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ddobagi3.R
import com.example.ddobagi3.model.JusoResponse
import com.example.ddobagi3.network.JusoRetrofitClient
import retrofit2.Call
import retrofit2.Response

class JusoActivity : AppCompatActivity() {
    private val APIKey = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juso)
        val call : Call<JusoResponse> = JusoRetrofitClient.instance.GetData.getXYByJuso(APIKey,1,10,"청계")

        call.enqueue(object : retrofit2.Callback<JusoResponse>{
            override fun onResponse(call: Call<JusoResponse>, response: Response<JusoResponse>) {
                Log.d("Success",response.body().toString())
            }

            override fun onFailure(call : Call<JusoResponse>, t : Throwable) {
                Log.d("Fail",t.toString())
            }
        })
    }
}