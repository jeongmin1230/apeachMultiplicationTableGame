package com.example.apeachmultiplicationtablegame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    /* 전역변수들 */

    // 뒤로가기 누른 시간용
    var lastTimeBackPressed : Long = 0

    lateinit var tvTimer : TextView
    lateinit var tvQuestion : TextView
    lateinit var etAnswer : EditText
    lateinit var tvRight : TextView
    lateinit var tvCount : TextView
    lateinit var btnPause : Button

    lateinit var tvLifeCount : TextView

    lateinit var iv : ImageView

    // 타이머를 위해 선언

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null

    // 문제 출력을 위한 변수
    var ran1 = 0
    var ran2 = 0

    // 맞힌 문제 수 count 를 위해 선언
    var count = 0

    // 목숨 수를 위해 선언
    var lifeCount = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "시작부분")

        timerStart()

        init()
        quiz()

        etAnswer.setOnEditorActionListener(editDoneActionListener(etAnswer))

    }

    // 뒤로가기
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
        tvTimer = findViewById(R.id.tvTimer)
        tvQuestion = findViewById(R.id.tvQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        tvRight = findViewById(R.id.tvRight)
        tvCount = findViewById(R.id.tvCount)
        btnPause = findViewById(R.id.btnPause)

        tvLifeCount = findViewById(R.id.tvLifeCount)

        iv = findViewById(R.id.iv)

        tvCount.text = "0 개"
    }
    // 타이머 시작
    private fun timerStart() {
        val intent = Intent(this, FinishActivity::class.java)
        isRunning = true // 타이머가 시작하면 isRunning 은 true 가 됨
        timerTask =
            kotlin.concurrent.timer(period = 10) { //반복주기는 peroid 프로퍼티로 설정, 단위는 1000분의 1초 (period = 1000, 1초)로 1초마다 흐르게 함
                time++
                val sec = time / 100 // time/100, 나눗셈의 몫(초 부분)
                if(sec == 20 && lifeCount >= 1 && count < 10) { // 초가 20이 되고, 생명이 1개 이상이고, count 가 10 미만일경우
                    tvTimer.setTextColor(Color.RED)
                    tvTimer.text="타임오버"
                    tvQuestion.text = "시간초과"
                    intent.putExtra("count", count) // 맞힌 개수가 finish 인텐트로 넘어가게 putExtra 사용
                    startActivity(intent)
                }
                // UI 조작을 위한 메소드
                runOnUiThread{
                    tvTimer.text = "$sec 초 경과"
                }
            }
    }

    // 일시정지
    private fun timerPause() {
        timerTask?.cancel()
        isRunning = false // 일시정지하면 isRunning 은 false 가 됨
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
                // 이 안에 count 10개 넘으면 액티비티 넘기는 코드?
                if(count >= 10) {
                    val intent = Intent(this, ClearActivity::class.java)
                    startActivity(intent)
                }
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

    // 게임 화면의 일시정지 버튼 눌렀을 때
    fun onClickPause(view: View) {
        timerPause()
        val dialog = PauseDialog(this)
        dialog.showDig()
    }

    // 다이얼로그 속 재시작
    fun onClickRestart(view: View) {
        timerStart()
    }

    // 다이얼로그 속 메인으로
    fun onClickToMain(view: View) {
        finish()
    }
    // 다이얼로그 속 종료하기
    fun onClickExit(view: View) {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        exitProcess(0)
    }


}