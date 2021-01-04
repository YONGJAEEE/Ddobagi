package com.example.ddobagi3.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ddobagi3.model.Documents
import com.example.ddobagi3.model.JusoResponse
import com.example.ddobagi3.network.JusoRetrofitClient
import retrofit2.Call
import retrofit2.Response

class JusoViewModel : ViewModel() {
    private val APIKey = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7"
    var jusoList = MutableLiveData<ArrayList<Documents>>()

    fun getJusoByText(text : String){
        val call : Call<JusoResponse> = JusoRetrofitClient.instance.GetData.getXYByJuso(APIKey,1,20,text)
        call.enqueue(object : retrofit2.Callback<JusoResponse> {
            override fun onResponse(call: Call<JusoResponse>, response: Response<JusoResponse>) {
                Log.d("Success", response.body().toString())
                if (response.body()?.documents!=null){
                    jusoList.value = response.body()?.documents as ArrayList<Documents>
                }
            }
            override fun onFailure(call: Call<JusoResponse>, t: Throwable) {
                Log.d("Fail", t.toString())
            }
        })
    }
}