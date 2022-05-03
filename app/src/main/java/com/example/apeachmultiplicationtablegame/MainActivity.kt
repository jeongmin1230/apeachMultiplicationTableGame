package com.example.apeachmultiplicationtablegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var etQuestion : TextView // 문제 창
    lateinit var etAnswer : EditText // 정답 입력 창
    lateinit var etRight : TextView // 정답 판별 창
    lateinit var etCount : TextView // 정답 개수 창

    lateinit var iv1 : ImageView // 목숨1
    lateinit var iv2 : ImageView // 목숨2
    lateinit var iv3 : ImageView // 목숨3
    lateinit var iv4 : ImageView // 목숨4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        etQuestion = findViewById(R.id.tvQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        etRight = findViewById(R.id.tvRight)
        etCount = findViewById(R.id.tvCount)

        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        iv4 = findViewById(R.id.iv4)

    }

    fun onClickDone(view: View) {

        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)

    }
}