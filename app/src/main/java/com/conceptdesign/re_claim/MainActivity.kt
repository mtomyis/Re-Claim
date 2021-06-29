package com.conceptdesign.re_claim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.conceptdesign.re_claim.Adapter.ListMyClaimAdapter
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.Model.Reimbursement
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    internal lateinit var db:DBHelper
    internal var lstReimb:List<Reimbursement> = ArrayList<Reimbursement>()
    companion object {
        var ID: String= "ID"
        val REIMBUST: String = "REIMBUST"
        val TANGGAL: String = "TANGGAL"
        val TOTAL: String = "TOTAL"
        val STATUS: String = "STATUS"

        val KEPERLUAN: String = "KEPERLUAN"
        val MILIK: String = "MILIK"
        val NOMINAL: String = "NOMINAL"
        val SRC: String = "SRC"
        val FK: String = "FK"

    }


    override fun onStart() {
        tampilkanData()
        super.onStart()
    }

    fun onItemClicked(get: Reimbursement?){
//        Toast.makeText(this, "klick "+get?.id, Toast.LENGTH_LONG).show()
        Log.d("jes", get?.id.toString())
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra(ID, get?.id)
        intent.putExtra(REIMBUST, get?.reimburs)
        intent.putExtra(TANGGAL, get?.tgl)
        intent.putExtra(TOTAL, get?.total)
        intent.putExtra(STATUS, get?.status)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DBHelper(this)
    }

    fun tampilkanData(){
        lstReimb = db.allReimburs
//        Log.d("qwqwqwqw", lstReimb.get(0).status.toString())
        id_rv_main.adapter=ListMyClaimAdapter(lstReimb, this@MainActivity)
    }

    fun btn_createnew(view: View) {
        intent = Intent(applicationContext, CreateActivity::class.java)
        startActivity(intent)
    }

}