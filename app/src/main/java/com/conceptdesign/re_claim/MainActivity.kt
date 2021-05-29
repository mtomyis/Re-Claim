package com.conceptdesign.re_claim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btn_createnew(view: View) {
        intent = Intent(applicationContext, CreateActivity::class.java)
        startActivity(intent)
    }

}