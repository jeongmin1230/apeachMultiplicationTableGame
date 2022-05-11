package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class FinishActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        questionCount()
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

    // 시간이 다 되어서 넘어갔을 경우
    private fun questionCount() {
        var tvComment : TextView = findViewById(R.id.tvComment)
        if(intent.hasExtra("count")) {
//            Log.d(TAG, "questionCount - 넘어온 개수 : " + intent.getIntExtra("count", 0))
            var lack = 10 - intent.getIntExtra("count", 0)

            tvComment.text = "게임이 끝났습니다.\n 맞은 총 개수는 " + intent.getIntExtra("count", 0)+"개 입니다.\n\n 클리어 하기에  $lack 개 부족하네요.."
        }
        else { // intent.hasExtra("count") 를 찾지 못했을 경우 0이 출력됨
//            Log.d(TAG, "questionCount - 넘어온 개수 : " + intent.getIntExtra("count", 0))
            intent.getIntExtra("count", 0)
            tvComment.text = "개수를 찾을 수 없습니다..\n 죄송하지만 다시 해주세요.."
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