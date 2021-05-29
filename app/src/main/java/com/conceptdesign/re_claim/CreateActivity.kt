package com.conceptdesign.re_claim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }

    fun btn_tambahbiaya(view: View) {
        intent = Intent(applicationContext, AddbiayaActivity::class.java)
        startActivity(intent)
    }
}