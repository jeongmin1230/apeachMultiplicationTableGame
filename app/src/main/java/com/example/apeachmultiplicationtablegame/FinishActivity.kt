package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat

class FinishActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        questionCount()
    }
    // 시간이 다 되어서 넘어갔을 경우
    private fun questionCount() {
        var tvComment : TextView = findViewById(R.id.tvComment)
        if(intent.hasExtra("count")) {
            Log.d(TAG, "questionCount - 넘어온 개수 : " + intent.getIntExtra("count", 0))
            intent.getIntExtra("count", 0)

            tvComment.text = "게임이 끝났습니다.\n 맞은 총 개수는 " + intent.getIntExtra("count", 0)+"개 입니다.\n"
        }
        else { // intent.hasExtra("count") 를 찾지 못했을 경우 0이 출력됨
            Log.d(TAG, "questionCount - 넘어온 개수 : " + intent.getIntExtra("count", 0))
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
        System.exit(0) // 프로세스를 종료
    }
}