package com.conceptdesign.re_claim

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesign.re_claim.Adapter.ListMyClaimAdapterDetail
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.MainActivity.Companion.FK
import com.conceptdesign.re_claim.MainActivity.Companion.ID
import com.conceptdesign.re_claim.MainActivity.Companion.KEPERLUAN
import com.conceptdesign.re_claim.MainActivity.Companion.MILIK
import com.conceptdesign.re_claim.MainActivity.Companion.NOMINAL
import com.conceptdesign.re_claim.MainActivity.Companion.REIMBUST
import com.conceptdesign.re_claim.MainActivity.Companion.SRC
import com.conceptdesign.re_claim.MainActivity.Companion.STATUS
import com.conceptdesign.re_claim.MainActivity.Companion.TANGGAL
import com.conceptdesign.re_claim.MainActivity.Companion.TOTAL
import com.conceptdesign.re_claim.Model.DetailReimbursment
import com.conceptdesign.re_claim.Model.M_reimbusment
import com.conceptdesign.re_claim.Model.Reimbursement
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UpdateActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var lstDetailReimb:List<DetailReimbursment> = ArrayList<DetailReimbursment>()

    lateinit var tanggalawal : String
    lateinit var tanggalfix : String

    override fun onStart() {
        tampilkanData()
        super.onStart()
    }

    fun onItemClicked(get: DetailReimbursment?){
//        Toast.makeText(this, "klick "+get?.id, Toast.LENGTH_LONG).show()
//        Log.d("jes", get?.id.toString())
        val intent = Intent(this, UpdatebiayaActivity::class.java)
        intent.putExtra(ID, get?.id)
        intent.putExtra(KEPERLUAN, get?.keperluan)
        intent.putExtra(TANGGAL, get?.tgl)
        intent.putExtra(NOMINAL, get?.nominal)
        intent.putExtra(SRC, get?.src)
        intent.putExtra(MILIK, get?.milik)
        intent.putExtra(FK, get?.fk)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

//        Log.d("reim : ", ""+intent.getIntExtra(ID,0).toString())
//
        val namareimbus = findViewById(R.id.ed_nama_reimburse) as EditText
        val tglreimbus = findViewById(R.id.ed_tgl_reimburs) as EditText

        namareimbus.setText(intent.getStringExtra(REIMBUST)!!.toString())
        id_total_reimbus.setText(intent.getStringExtra(TOTAL)!!.toString())

//        hidden btn
        btn_simpan_reimburs.visibility = View.GONE
        btn_edit_reimburs.visibility = View.VISIBLE
        btn_tambahbiaya.visibility = View.VISIBLE

        //input tanggal a
        // set tanggal awal hari ini
        val dateInString = intent.getStringExtra(TANGGAL)!!.toString()
        val simpleFormat =  DateTimeFormatter.ISO_DATE;
        val convertedDate = LocalDate.parse(dateInString, simpleFormat)
        tglreimbus.setText("$convertedDate")
        tanggalfix="$convertedDate"

        val myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
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

            tanggalfix = "" + (dpersem!!.year + 1900).toString() + "-" + (dpersem!!.month + 1).toString() + "-" + dpersem!!.date

        }
        ed_tgl_reimburs.setOnClickListener(View.OnClickListener { DatePickerDialog(this, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show() })

        //input tanggal b

        db = DBHelper(this)
        //event
        btn_edit_reimburs.setOnClickListener {
            if (!(namareimbus.text.toString().equals("")) && !(tglreimbus.text.toString().equals(""))){
                val edit_reimbursement = M_reimbusment(
                        intent.getIntExtra(ID,0),
                        tanggalfix,
                        namareimbus.text.toString(),
                        intent.getIntExtra(STATUS,0),
                        "0" //liat count db sqlite
                )
                Log.d("reim : ", ""+edit_reimbursement.reimburs)
                db.updateReimburs(edit_reimbursement)
                Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Data Belum Terisi", Toast.LENGTH_LONG).show()
            }
        }

        btn_tambahbiaya.setOnClickListener {
            val idreim = intent.getIntExtra(ID,0)
//            Log.d("id : ", ""+db.getOneReimburs.id+" nama : "+db.getOneReimburs.reimburs)
            intent = Intent(applicationContext, AddbiayaActivity::class.java)
            intent.putExtra(ID, idreim)
            startActivity(intent)
        }
    }

    fun tampilkanData(){
        lstDetailReimb = db.allDetailReimburs(intent.getIntExtra(ID,0))
//        Log.d("qwqwqwqw", lstDetailReimb.get(0).nominal.toString())
        rv_list_biaya.adapter= ListMyClaimAdapterDetail(lstDetailReimb, this@UpdateActivity)
    }
}