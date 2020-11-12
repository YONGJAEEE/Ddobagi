package com.example.ddobagi3.adpater


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.model.Documents
import com.example.ddobagi3.view.JusoActivity
import com.example.ddobagi3.view.WriteActivity

class AdressAdapter(val AdressList : ArrayList<Documents>) : RecyclerView.Adapter<AdressAdapter.Holder>(){
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jusoText = itemView.findViewById<TextView>(R.id.tv_juso)

        fun bind(juso : Documents) {
            jusoText.text = juso.address_name

            itemView.setOnClickListener{
                val intent = Intent(itemView.context,WriteActivity::class.java)
                intent.putExtra("adressName",juso.address_name)
                intent.putExtra("x",juso.x)
                intent.putExtra("y",juso.y)
                itemView.context.startActivity(intent)
            }
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