package com.conceptdesign.re_claim.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.conceptdesign.re_claim.Model.Reimbursement

class DBHelper(context:Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {
    companion object{
        private val DATABASE_NAME = "reimbursement.db"
        private val DATABASE_VER = 1

        //table
        private val TABLE_REIMBURS = "reimbursement"
        private val COL_ID_REIMBURS = "id"
        private val COL_NAME_REIMBURS = "name"
        private val COL_TGL_REIMBURS = "tanggal"
        private val COL_STATUS_REIMBURS = "status"
        private val COL_TOTAL = "total"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_REIMBURS ($COL_ID_REIMBURS INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME_REIMBURS TEXT, " +
                "$COL_TGL_REIMBURS DATETIME, $COL_TOTAL text, $COL_STATUS_REIMBURS INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)

        val INSERT_TABLE_QUERY = ("INSERT INTO $TABLE_REIMBURS($COL_ID_REIMBURS,$COL_NAME_REIMBURS,$COL_TGL_REIMBURS,$COL_TOTAL,$COL_STATUS_REIMBURS) "+
                "VALUES (1,'Perjalanan Banyuwangi','2021-06-25', '230000',1)")
        db!!.execSQL(INSERT_TABLE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REIMBURS")
        onCreate(db!!)
    }

    //CRUD
    val allReimburs:List<Reimbursement>
        get() {
            val lstReimburs = ArrayList<Reimbursement>()
            val selectQuery = "SELECT*FROM $TABLE_REIMBURS"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst())
            {
                do {
                    val reimbursement = Reimbursement()
                    reimbursement.reimburs = cursor.getString(cursor.getColumnIndex(COL_NAME_REIMBURS))
                    reimbursement.tgl = cursor.getString(cursor.getColumnIndex(COL_TGL_REIMBURS))
                    reimbursement.total = cursor.getString(cursor.getColumnIndex(COL_TOTAL))
                    reimbursement.status = cursor.getInt(cursor.getColumnIndex(COL_STATUS_REIMBURS))

                    lstReimburs.add(reimbursement)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstReimburs
        }

    fun addReimburs(reimbursement: Reimbursement)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME_REIMBURS,reimbursement.reimburs)
        values.put(COL_TGL_REIMBURS,reimbursement.tgl)
        values.put(COL_TOTAL,reimbursement.total)
        values.put(COL_STATUS_REIMBURS,reimbursement.status)
        db.insert(TABLE_REIMBURS,null,values)
        db.close()
    }

    fun updateReimburs(reimbursement: Reimbursement):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
//        values.put(COL_ID_REIMBURS,reimbursement.id)
        values.put(COL_NAME_REIMBURS,reimbursement.reimburs)
        values.put(COL_TGL_REIMBURS,reimbursement.tgl)
        values.put(COL_TOTAL,reimbursement.total)
        values.put(COL_STATUS_REIMBURS,reimbursement.status)

        return db.update(TABLE_REIMBURS, values,"$COL_ID_REIMBURS=?", arrayOf(reimbursement.id.toString()))
    }

    fun deleteReimburs(reimbursement: Reimbursement)
    {
        val db = this.writableDatabase
        db.delete(TABLE_REIMBURS,"$COL_ID_REIMBURS=?", arrayOf(reimbursement.id.toString()))
        db.close()
    }
}