package com.conceptdesign.re_claim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.Model.Reimbursement
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*

class CreateActivity : AppCompatActivity() {

    internal lateinit var db:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        db = DBHelper(this)
        //event
        btn_simpan_reimburs.setOnClickListener {
            Toast.makeText(this, "tgl : "+ed_tgl_reimburs, Toast.LENGTH_LONG).show()
//            if (ed_nama_reimburse!=null && ed_tgl_reimburs!=null){
//                val add_reimbursement = Reimbursement(
//                        0,
//                        ed_tgl_reimburs.text.toString(),
//                        ed_nama_reimburse.text.toString(),
//                        0,
//                        0
//                )
//                db.addReimburs(add_reimbursement)
//            }
        }
    }

    fun btn_tambahbiaya(view: View) {
        intent = Intent(applicationContext, AddbiayaActivity::class.java)
        startActivity(intent)
    }

}