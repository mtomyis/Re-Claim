package com.conceptdesign.re_claim

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.conceptdesign.re_claim.Adapter.ListMyClaimAdapter
import com.conceptdesign.re_claim.DBHelper.DBHelper
import com.conceptdesign.re_claim.Model.Reimbursement
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    internal lateinit var db:DBHelper
    private var username = ""
    internal var lstReimb:List<Reimbursement> = ArrayList<Reimbursement>()
    companion object {
        var ID: String= "ID"
        val REIMBUST: String = "REIMBUST"
        val TANGGAL: String = "TANGGAL"
        val TOTAL: String = "TOTAL"
        val STATUS: String = "STATUS"
        val SALDO: String = "SALDO"

        val KEPERLUAN: String = "KEPERLUAN"
        val MILIK: String = "MILIK"
        val NOMINAL: String = "NOMINAL"
        val SRC: String = "SRC"
        val FK: String = "FK"

        fun rupiah(number: Double): String{
            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            return numberFormat.format(number).toString()
        }
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
        intent.putExtra(SALDO, get?.saldo)
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

        textView8.setText(db.ambilNama())
    }

    fun btn_createnew(view: View) {
        intent = Intent(applicationContext, CreateActivity::class.java)
        startActivity(intent)
    }

    fun showdialog(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Nama")

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Masukan Nama")
        input.setText(db.ambilNama())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            username = input.text.toString()
            db.updateusername(username)
            onStart()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    fun btnusrename(view: View) {
        showdialog()
    }

}