package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class ClearActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clear)
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

    fun onClickReplay(view: View) {
        val intent = Intent(this, IntroActivity::class.java)

        startActivity(intent)
    }
    fun onClickFinish(view: View) {
        ActivityCompat.finishAffinity(this) // 액티비티를 종료하고
        exitProcess(0) // 프로세스를 종료
    }
}