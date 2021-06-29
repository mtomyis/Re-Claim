package com.conceptdesign.re_claim

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.Model.M_reimbusment
import kotlinx.android.synthetic.main.activity_create.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CreateActivity : AppCompatActivity() {

    internal lateinit var db:DBHelper
    lateinit var tanggalawal : String
    lateinit var tanggalfix : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val namareimbus = findViewById(R.id.ed_nama_reimburse) as EditText
        val tglreimbus = findViewById(R.id.ed_tgl_reimburs) as EditText

//        hidden btn
        btn_tambahbiaya.visibility = View.GONE

        //input tanggal a
        // set tanggal awal hari ini
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        ed_tgl_reimburs.setText(currentDate)

//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//
//        ed_tgl_reimburs.setOnClickListener {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val klik = DatePickerDialog(
//                        this,
//                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                            ed_tgl_reimburs.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
//                            val newDate:Calendar =Calendar.getInstance()
//                            newDate.set(year, (month + 1), dayOfMonth)
//                        },
//                        year,
//                        month,
//                        day
//                )
//                klik.show()
//            } else {
//                Log.d("eror version android", "wah eror")
//            }
//        }

        val myCalendar = Calendar.getInstance()
        val date = OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            ed_tgl_reimburs.setText(sdf.format(myCalendar.time))
            tanggalawal = ed_tgl_reimburs.getText().toString().trim()
            //int tgl = Integer.parseInt(ambiltanggalnya);
            var dpersem: Date? = null
            try {
                dpersem = sdf.parse(tanggalawal)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            //Toast.makeText(LihatPengingatPadiActivity.this, "tanggal: "+ dpersem.getDate() +"/"+(dpersem.getMonth()+1) +"/"+ (dpersem.getYear()+1900), Toast.LENGTH_SHORT).show();
            tanggalfix = "" + (dpersem!!.year + 1900).toString() + "-" + (dpersem!!.month + 1).toString() + "-" + dpersem!!.date

            //Toast.makeText(LihatPengingatPadiActivity.this, "tanggal: "+tanggalpersemaian, Toast.LENGTH_SHORT).show();
        }
        ed_tgl_reimburs.setOnClickListener(View.OnClickListener { DatePickerDialog(this, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show() })
        //input tanggal b

        db = DBHelper(this)
        //event
        btn_simpan_reimburs.setOnClickListener {
            if (!(namareimbus.text.toString().equals("")) && !(tglreimbus.text.toString().equals(""))){
//                Log.d("reim : ", ""+namareimbus.text.toString())
                val add_reimbursement = M_reimbusment(
                        0,
                        tanggalfix,
                        namareimbus.text.toString(),
                        0,
                        "0"
                )
//                Log.d("reim : ", ""+add_reimbursement.reimburs)
                db.addReimburs(add_reimbursement)
                Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_LONG).show()
                finish() //kembali ke main
//                btn_tambahbiaya.visibility = View.VISIBLE
//                btn_simpan_reimburs.visibility = View.GONE
//                btn_edit_reimburs.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(this, "Data Belum Terisi", Toast.LENGTH_LONG).show()
            }
        }

//        btn_tambahbiaya.setOnClickListener {
////            Log.d("id : ", ""+db.getOneReimburs.id+" nama : "+db.getOneReimburs.reimburs)
//            intent = Intent(applicationContext, AddbiayaActivity::class.java)
//            startActivity(intent)
//        }
    }

}