package com.conceptdesign.re_claim

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.Model.Reimbursement
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {

    internal lateinit var db:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //input tanggal a
        // set tanggal awal hari ini
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        ed_tgl_reimburs.setText(currentDate)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        ed_tgl_reimburs.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val klik = DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            ed_tgl_reimburs.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
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

        db = DBHelper(this)
        //event
        btn_simpan_reimburs.setOnClickListener {
//            Toast.makeText(this, "tgl : "+ed_tgl_reimburs, Toast.LENGTH_LONG).show()
            if (ed_nama_reimburse!=null && ed_tgl_reimburs!=null){
                val add_reimbursement = Reimbursement(
                        0,
                        ed_tgl_reimburs.text.toString(),
                        ed_nama_reimburse.text.toString(),
                        0,
                        "0"
                )
                db.addReimburs(add_reimbursement)
            }
        }
    }

    fun btn_tambahbiaya(view: View) {
        intent = Intent(applicationContext, AddbiayaActivity::class.java)
        startActivity(intent)
    }

}