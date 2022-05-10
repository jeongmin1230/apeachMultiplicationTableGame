package com.example.apeachmultiplicationtablegame

import android.content.Context
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
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    /* 전역변수들 */

    lateinit var tvQuestion : TextView
    lateinit var etAnswer : EditText
    lateinit var tvRight : TextView
    lateinit var tvCount : TextView
    lateinit var btnFinish : Button

    lateinit var tvLifeCount : TextView

    lateinit var iv : ImageView

    // 타이머를 위해 선언
    lateinit var tvTimer:TextView

    var timer = 0
    var timerStarted = false
    var started = false

    // 문제 출력을 위한 변수
    var ran1 = 0
    var ran2 = 0

    // 맞힌 문제 수 count 를 위해 선언
    var count = 0

    // 목숨 수를 위해 선언
    var lifeCount = 4

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
        quiz()

        etAnswer.setOnEditorActionListener(editDoneActionListener(etAnswer))
    }
    // 뒤로가기
    var lastTimeBackPressed : Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_LONG).show() }
        else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            exitProcess(0)
        }
    }
    // onCreate 되었을 때 구성요소를 찾아오는 함수
    private fun init() {
        tvQuestion = findViewById(R.id.tvQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        tvRight = findViewById(R.id.tvRight)
        tvCount = findViewById(R.id.tvCount)
        btnFinish = findViewById(R.id.btnFinish)

        tvLifeCount = findViewById(R.id.tvLifeCount)

        iv = findViewById(R.id.iv)

        tvCount.text = "0 개"
    }
    // 타이머 시작 함수
    private fun timerStart(){
        tvTimer = findViewById(R.id.tvTimer)
        timer = 20
        timerStarted = true
        thread(start=true){
            while(timer >= 0){
                handler.sendEmptyMessage(0)
                Thread.sleep(1000)
                if(!timerStarted) {
                    val intent = Intent(this, FinishActivity::class.java)
                    tvTimer.setTextColor(Color.RED)
                    tvTimer.text = "타임오버"
                    // 타이머가 끝나면 finish 화면으로 자동 전환,
                    intent.putExtra("count", count) // 맞힌 개수가 finish 인텐트로 넘어가게 putExtra 사용
//                    Log.d(TAG, "timerStart 안 - finish 인텐트로 넘길 count : $count")
                    if(count >= 10) {
                        val intent = Intent(this, ClearActivity::class.java)
                        startActivity(intent)
                    } else if(lifeCount >= 1){ // 목숨이 한 개 이상일 경우에만 FinishActivity 로 넘어가도록 구현
                        startActivity(intent)
                    }
//                    Log.d(TAG, "타이머 끝남")
                    break
                }
            }
        }
    }
    // 완료 버튼 눌렀을 경우 리스너
    private fun editDoneActionListener(view:View) : TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener{ textView, actionId, keyEvent ->

            if(actionId == EditorInfo.IME_ACTION_DONE) {
                showKeyBoard()
                view.callOnClick()
                right()
            }
            false
        }
    }
    // 키보드 유지하는 함수
    private fun showKeyBoard() {
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())
    }
    // 구구단 생성 함수
    private fun randomMultiplication() : String {

        ran1 = (2..9).random() // 2<= ran1 <= 9 몇단
        ran2 = (2..9).random() // 2<= ran2 <= 9 x 몇

//        Log.d(TAG, "randomMultiplication 안 - ran1 : $ran1, ran2 : $ran2")

        return "$ran1 X $ran2"
    }
    // 문제 출력 함수
    private fun quiz() {
        tvQuestion.text = randomMultiplication()
    }
    // 입력값 판별 함수
    private fun right() {
        Log.d(TAG, "right 안 : ran1 : $ran1, ran2 : $ran2")

        var changeEtAnswer = etAnswer.text.toString()
//        Log.d(TAG, "right 안 : changeEtAnswer 입력 값: $changeEtAnswer")
        if(changeEtAnswer.isEmpty()) { // 입력값이 없을 경우
            Toast.makeText(this, "값을 입력해주세요!", Toast.LENGTH_SHORT).show()
        }
        else { // 입력값이 있을 경우
            if(changeEtAnswer.toInt() == ran1*ran2 ) {
                tvRight.setTextColor(Color.DKGRAY)
                tvRight.text="정답"
                count += 1
                tvCount.text = "$count 개"
                etAnswer.text.clear()
                quiz()
            } else { // 판별 틀렸을 경우 목숨 하나씩 깎이게 하기
                lifeCount -= 1
                tvRight.setTextColor(Color.BLUE)
                tvRight.text="오답"
                tvCount.text = "$count 개"
                etAnswer.text.clear()
                quiz()
                tvLifeCount.text = "$lifeCount"
                // 목숨이 0개가 될 경우 게임 오버
                if(lifeCount == 0) {
                    finish()
                    var intent = Intent(this, GameOverActivity::class.java)
                    startActivity(intent)
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