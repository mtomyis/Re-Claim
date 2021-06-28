package com.conceptdesign.re_claim

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_addbiaya.*
import java.text.SimpleDateFormat
import java.util.*

class AddbiayaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbiaya)

        val keperluan = findViewById(R.id.ed_keperluan) as EditText
        val tglreimbus = findViewById(R.id.ed_tglbiaya) as EditText
        val ed_nominal = findViewById(R.id.ed_nominal) as EditText

        //input tanggal a
        // set tanggal awal hari ini
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        ed_tglbiaya.setText(currentDate)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        ed_tglbiaya.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val klik = DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            ed_tglbiaya.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
                        },
                        year,
                        month,
                        day
                )
                klik.show()
            } else {
                Log.d("eror version android", "wah eror")
            }
        }
        //input tanggal b
        // event
        btn_simpanbiaya.setOnClickListener {
            val idr: Int = RG.checkedRadioButtonId
            if (idr!=-1 // If any radio button checked from radio group
                    && !(keperluan.text.toString().equals(""))
                    && !(tglreimbus.text.toString().equals(""))
                    && !(ed_nominal.text.toString().equals(""))){
                // Get the instance of radio button using id
                val radio: RadioButton = findViewById(idr)
                Toast.makeText(applicationContext,"On button click :" +" ${radio.text}",Toast.LENGTH_SHORT).show()
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"Data Belum Lengkap",
                        Toast.LENGTH_SHORT).show()
            }
        }


    }
}