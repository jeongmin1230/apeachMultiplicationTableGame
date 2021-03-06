package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class IntroActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
    // 뒤로가기
    var lastTimeBackPressed : Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show() }
        else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            exitProcess(0)
        }
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