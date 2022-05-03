package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    fun onClickHowToPlay(view: View) {
        val dialog = CustomDialog(this)
        dialog.showDig() // 다이얼로그 밖 영역을 터치하면 꺼지게 설정
    }
    fun onClickPlay(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}