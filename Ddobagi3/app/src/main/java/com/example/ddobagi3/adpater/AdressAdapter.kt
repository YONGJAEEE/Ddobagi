package com.example.ddobagi3.adpater


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.Documents

class AdressAdapter(val AdressList : ArrayList<Documents>) : RecyclerView.Adapter<AdressAdapter.Holder>(){
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jusoText = itemView.findViewById<TextView>(R.id.tv_juso)

        fun bind(juso : Documents) {
            Log.d("TAG", juso.toString())
            jusoText.text = juso.address_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adressitem, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(AdressList[position])
    }

    override fun getItemCount(): Int {
        return AdressList.size
    }
}