package com.example.ddobagi3.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.ddobagi3.R
import com.example.ddobagi3.adpater.AdressAdapter
import com.example.ddobagi3.adpater.DiaryAdapter
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.widget.MyApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private var buttonIcon = 0
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        val formatted = current.format(formatter)
        TodayDate.text = formatted
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        recyclerview.adapter = DiaryAdapter()

        Log.d("TAG",MyApplication.prefs.getString("uid","null"))

        btn_float.setButtonIconResource(R.drawable.ic_open)
        btn_float.speedDialMenuAdapter = speedDialMenuAdapter
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    private val speedDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount() = 3

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem =
            when (position) {
                0 -> SpeedDialMenuItem(context, R.drawable.write, "Write Diary")
                1 -> SpeedDialMenuItem(context, R.drawable.ic_aboutme, "About Developer")
                2 -> SpeedDialMenuItem(context, R.drawable.ic_aboutme, "SignOut")
                else -> throw IllegalArgumentException("No Menu Item")
            }

        override fun fabRotationDegrees(): Float = if (buttonIcon == 0) 135F else 0F

        override fun onMenuItemClick(position: Int): Boolean {
            when (position) {
                0 -> startActivity(Intent(this@MainActivity, JusoActivity::class.java))
                1 -> startActivity(Intent(this@MainActivity, AboutMeActivity::class.java))
                2 -> {
                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient.signOut()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
            return true
        }
    }
}