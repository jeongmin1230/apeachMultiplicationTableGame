package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
    }

    fun onClickReplay(view: View) {
        val intent = Intent(this, IntroActivity::class.java)

        startActivity(intent)
    }
    fun onClickFinish(view: View) {
        ActivityCompat.finishAffinity(this) // 액티비티를 종료하고
        System.exit(0) // 프로세스를 종료
    }
}