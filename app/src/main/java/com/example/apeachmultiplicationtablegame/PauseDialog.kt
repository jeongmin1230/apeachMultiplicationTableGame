package com.example.apeachmultiplicationtablegame

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager

class PauseDialog(context:Context) {
    private val dialog = Dialog(context)

    fun showDig() {
        dialog.setContentView(R.layout.dialog_pause)

        // Dialog 크기 설정
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        dialog.show()
    }
}