package com.example.ddobagi3.adpater

import android.content.Context
import android.content.Intent
import com.example.ddobagi3.R
import com.example.ddobagi3.view.JusoActivity
import com.example.ddobagi3.view.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.lang.IllegalArgumentException

class FabAdapter(val context: Context) : SpeedDialMenuAdapter() {
    private var buttonIcon = 0
    private lateinit var googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }
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
            0 -> context.startActivity(Intent(context, JusoActivity::class.java))
            1 -> {
                FirebaseAuth.getInstance().signOut()
                googleSignInClient.signOut()
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        }
        return true
    }
}