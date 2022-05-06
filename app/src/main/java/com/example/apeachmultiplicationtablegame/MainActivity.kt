package com.example.apeachmultiplicationtablegame

import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    /* 전역변수들 */

    lateinit var tvQuestion : TextView
    lateinit var etAnswer : EditText
    lateinit var tvRight : TextView
    lateinit var tvCount : TextView
    lateinit var btnFinish : Button

    lateinit var iv1 : ImageView
    lateinit var iv2 : ImageView
    lateinit var iv3 : ImageView
    lateinit var iv4 : ImageView

    var a = 0
    var b = 0
    var count = 0

    // 타이머를 위해 선언
    lateinit var tvTimer:TextView

    var timer = 0
    var timerStarted = false
    var started = false

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {

            if(started){
                when{
                    timerStarted -> {
                        tvTimer = findViewById(R.id.tvTimer)
                        tvTimer.text = "$timer 초 남음"
                        Log.d(TAG, "workTime : $timer")
                        timer -= 1
                        if(timer == -1){
                            timerStarted = false
                        }
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "시작부분")
        started = true

        timerStart()

        init()
//        quiz()

        etAnswer.setOnEditorActionListener(editDoneActionListener(etAnswer))
    }
    // onCreate 되었을 때 구성요소를 찾아오는 함수
    private fun init() {
        tvQuestion = findViewById(R.id.tvQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        tvRight = findViewById(R.id.tvRight)
        tvCount = findViewById(R.id.tvCount)
        btnFinish = findViewById(R.id.btnFinish)

        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        iv4 = findViewById(R.id.iv4)

    }
    // 완료 버튼 눌렀을 경우 리스너
    private fun editDoneActionListener(view:View) : TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener{ textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                etAnswer.text.clear()
                view.callOnClick()
            }
            false
        }
    }
    // 구구단 생성 함수
    private fun randomMultiplication() : String {

        val ran1 = (2..9).random() // 2<= ran1 <= 9 몇단
        val ran2 = (2..9).random() // 2<= ran2 <= 9 x 몇

        Log.d(TAG, "ran1 : $ran1, ran2 : $ran2")

        return "$ran1 X $ran2"
    }
    // 문제 출력 함수
    private fun quiz() {
        tvQuestion = findViewById(R.id.tvQuestion)
        tvQuestion.text = randomMultiplication()
        val problemArr = tvQuestion.text.split("X") // X 를 기준으로 분리해서 배열 0번, 1번에 넣음

        a = problemArr[0].toInt()
        b = problemArr[1].toInt()

    }
    // 입력값 판별 함수
//    private fun right() {
//        if(etAnswer.text.toString().toInt() == a*b ) {
//            tvRight.text="O"
//            count += 1
//            tvCount.text = "$count 개"
//            etAnswer.text.clear()
//            quiz()
//        } else {
//            tvRight.text="X"
//            tvCount.text = "$count 개"
//            etA
//        }
//    }
    // 타이머 시작 함수
    private fun timerStart(){
        tvTimer = findViewById(R.id.tvTimer)
        timer = 20
        timerStarted = true
        thread(start=true){
            while(timer >= 0){
                Log.d(TAG, "workTime : $timer")
                handler.sendEmptyMessage(0)
                Thread.sleep(1000)
                if(!timerStarted) {
                    tvTimer.setTextColor(Color.RED)
                    tvTimer.text = "타임오버"
                    Log.d(TAG, "타이머 끝남")
                    break
                }
            }
        }
    }
    // 끝내기 버튼 눌렀을 때
    fun onClickDone(view: View) {

        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)

    }
}