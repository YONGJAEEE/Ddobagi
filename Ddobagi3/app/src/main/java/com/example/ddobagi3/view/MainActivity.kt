package com.example.ddobagi3.view
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ddobagi3.R
import com.example.ddobagi3.adpater.DiaryAdapter
import com.example.ddobagi3.model.DiaryData
import com.example.ddobagi3.widget.MyApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
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
    var diaryList : ArrayList<DiaryData> = arrayListOf()
    lateinit var firestore : FirebaseFirestore
    var backKeyPressedTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()
        val ref = firestore.collection("USER")
            .document(MyApplication.prefs.getString("uid","null"))
            .collection("diary")
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        val formatted = current.format(formatter)
        TodayDate.text = formatted
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        btn_float.setButtonIconResource(R.drawable.ic_open)
        btn_float.speedDialMenuAdapter = speedDialMenuAdapter
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        Log.d("TAG",MyApplication.prefs.getString("uid","null"))
        val diarytAdapter = DiaryAdapter(diaryList)
        recyclerview.adapter = diarytAdapter

        ref.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener() {querySnapshot, _->
            diaryList.clear()
            for (snapshot in querySnapshot!!.documents) {
                val item = DiaryData(
                    snapshot.get("documentId").toString(),
                    snapshot.get("title").toString(),
                    snapshot.get("date").toString(),
                    snapshot.get("weather").toString(),
                    snapshot.get("location").toString(),
                    snapshot.get("content").toString()
                )
                diaryList.add(item)
            }
            if (diaryList.size == 0){
                tv_noDiary.text = "작성한 일기가 없습니다."
            }else {
                tv_noDiary.text = ""

                diarytAdapter.notifyDataSetChanged()
            }
        }

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

    private val speedDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount() = 2

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem =
            when (position) {
                0 -> SpeedDialMenuItem(context, R.drawable.write, "Write Diary")
                1 -> SpeedDialMenuItem(context, R.drawable.ic_aboutme, "SignOut")
                else -> throw IllegalArgumentException("No Menu Item")
            }

        override fun fabRotationDegrees(): Float = if (buttonIcon == 0) 135F else 0F

        override fun onMenuItemClick(position: Int): Boolean {
            when (position) {
                0 -> startActivity(Intent(this@MainActivity, JusoActivity::class.java))
                1 -> {
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