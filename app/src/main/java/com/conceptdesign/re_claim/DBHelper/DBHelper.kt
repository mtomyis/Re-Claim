package com.conceptdesign.re_claim.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.conceptdesign.re_claim.Model.DetailReimbursment
import com.conceptdesign.re_claim.Model.M_detailReimbusment
import com.conceptdesign.re_claim.Model.M_reimbusment
import com.conceptdesign.re_claim.Model.Reimbursement

class DBHelper(context:Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {
    companion object{
        private val DATABASE_NAME = "reimbursement.db"
        private val DATABASE_VER = 1

        //table reimbus
        private val TABLE_REIMBURS = "reimbursement"
        private val COL_ID_REIMBURS = "id"
        private val COL_NAME_REIMBURS = "name"
        private val COL_TGL_REIMBURS = "tanggal"
        private val COL_STATUS_REIMBURS = "status"
        private val COL_TOTAL = "total"

        //table detail_reimbust
        private val TABLE_DETAIL = "detail"
        private val COL_ID_DETAIL = "id"
        private val COL_KEPERLUAN_DETAIL = "keperluan"
        private val COL_MILIK_DETAIL = "milik"
        private val COL_NOMINAL_DETAIL = "nominal"
        private val COL_TANGGAL_DETAIL = "tgl"
        private val COL_SRC_DETAIL = "src"
        private val COL_FK_REIMBURS = "fk_reimburs"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_REIMBUS = ("CREATE TABLE $TABLE_REIMBURS ($COL_ID_REIMBURS INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME_REIMBURS TEXT, " +
                "$COL_TGL_REIMBURS DATETIME, $COL_TOTAL text, $COL_STATUS_REIMBURS INTEGER)")
        db!!.execSQL(CREATE_TABLE_REIMBUS)

        val CREATE_TABLE_DETAIL = ("CREATE TABLE $TABLE_DETAIL ($COL_ID_DETAIL INTEGER PRIMARY KEY AUTOINCREMENT, $COL_KEPERLUAN_DETAIL TEXT," +
                "$COL_MILIK_DETAIL TEXT, $COL_NOMINAL_DETAIL TEXT, $COL_TANGGAL_DETAIL DATETIME, $COL_SRC_DETAIL text, $COL_FK_REIMBURS INTEGER)")
        db!!.execSQL(CREATE_TABLE_DETAIL)

        val INSERT_TABLE_REIMBUS = ("INSERT INTO $TABLE_REIMBURS($COL_ID_REIMBURS,$COL_NAME_REIMBURS,$COL_TGL_REIMBURS,$COL_TOTAL,$COL_STATUS_REIMBURS) "+
                "VALUES (1,'Perjalanan Banyuwangi','2021-06-25', '230000',0)," +
                "(2,'Perjalanan Jakarta','2021-06-29', '350000',1)")
        db!!.execSQL(INSERT_TABLE_REIMBUS)

        val INSERT_TABLE_DETAIL = ("INSERT INTO $TABLE_DETAIL($COL_ID_DETAIL,$COL_KEPERLUAN_DETAIL,$COL_MILIK_DETAIL,$COL_NOMINAL_DETAIL,$COL_TANGGAL_DETAIL,$COL_SRC_DETAIL,$COL_FK_REIMBURS) "+
                "VALUES (1, 'Beli Bensin Mobil', 'Uang Pribadi', '150000', '2021-06-25', 'img_02984234.jpg', 1)," +
                "(2, 'Beli Bensin Mobil lagi', 'Uang Pribadi', '150000', '2021-06-25', 'img_02984234.jpg', 1)," +
                "(3, 'Beli Bensin Mobil dong', 'Uang Pribadi', '150000', '2021-06-29', 'img_02984234.jpg', 2)")
        db!!.execSQL(INSERT_TABLE_DETAIL)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REIMBURS")
        onCreate(db!!)
    }

    //CRUD Reimbusment a
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
                    reimbursement.id = cursor.getInt(cursor.getColumnIndex(COL_ID_REIMBURS))
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

    fun addReimburs(reimbursement: M_reimbusment)
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

    fun updateReimburs(reimbursement: M_reimbusment):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID_REIMBURS,reimbursement.id)
        values.put(COL_NAME_REIMBURS,reimbursement.reimburs)
        values.put(COL_TGL_REIMBURS,reimbursement.tgl)
        values.put(COL_TOTAL,reimbursement.total)
        values.put(COL_STATUS_REIMBURS,reimbursement.status)

        return db.update(TABLE_REIMBURS, values,"$COL_ID_REIMBURS=?", arrayOf(reimbursement.id.toString()))
    }

    fun deleteReimburs(reimbursement: M_reimbusment)
    {
        val db = this.writableDatabase
        db.delete(TABLE_REIMBURS,"$COL_ID_REIMBURS=?", arrayOf(reimbursement.id.toString()))
        db.close()
    }

    val getOneReimburs: Reimbursement
        get() {
            val reimbursement = Reimbursement()
            val selectQuery = "SELECT*FROM $TABLE_REIMBURS ORDER BY $COL_ID_REIMBURS DESC LIMIT 1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst())
            {
                    reimbursement.id = cursor.getInt(cursor.getColumnIndex(COL_ID_REIMBURS))
                    reimbursement.reimburs = cursor.getString(cursor.getColumnIndex(COL_NAME_REIMBURS))
                    reimbursement.tgl = cursor.getString(cursor.getColumnIndex(COL_TGL_REIMBURS))
                    reimbursement.total = cursor.getString(cursor.getColumnIndex(COL_TOTAL))
                    reimbursement.status = cursor.getInt(cursor.getColumnIndex(COL_STATUS_REIMBURS))
            }
            db.close()
            return reimbursement
        }
    //CRUD Reimbusment b

    //CRUD DetailReimbusment a
    val allDetailReimbursh:List<DetailReimbursment>
        get() {
            val lstDetailReimburs = ArrayList<DetailReimbursment>()
            val selectQuery = "SELECT*FROM $TABLE_DETAIL WHERE $COL_FK_REIMBURS = 1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst())
            {
                do {
                    val detail_reimbursement = DetailReimbursment()
                    detail_reimbursement. id = cursor.getInt(cursor.getColumnIndex(COL_ID_DETAIL))
                    detail_reimbursement.keperluan = cursor.getString(cursor.getColumnIndex(COL_KEPERLUAN_DETAIL))
                    detail_reimbursement.milik = cursor.getString(cursor.getColumnIndex(COL_MILIK_DETAIL))
                    detail_reimbursement.nominal = cursor.getString(cursor.getColumnIndex(COL_NOMINAL_DETAIL))
                    detail_reimbursement.tgl = cursor.getString(cursor.getColumnIndex(COL_TANGGAL_DETAIL))
                    detail_reimbursement.src = cursor.getString(cursor.getColumnIndex(COL_SRC_DETAIL))
                    detail_reimbursement.fk = cursor.getInt(cursor.getColumnIndex(COL_FK_REIMBURS))
                    lstDetailReimburs.add(detail_reimbursement)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstDetailReimburs
        }

    fun allDetailReimburs(idReimbus: Int): ArrayList<DetailReimbursment> {
        val lstDetailReimburs = ArrayList<DetailReimbursment>()
        val selectQuery = "SELECT*FROM $TABLE_DETAIL WHERE $COL_FK_REIMBURS = $idReimbus"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst())
        {
            do {
                val detail_reimbursement = DetailReimbursment()
                detail_reimbursement. id = cursor.getInt(cursor.getColumnIndex(COL_ID_DETAIL))
                detail_reimbursement.keperluan = cursor.getString(cursor.getColumnIndex(COL_KEPERLUAN_DETAIL))
                detail_reimbursement.milik = cursor.getString(cursor.getColumnIndex(COL_MILIK_DETAIL))
                detail_reimbursement.nominal = cursor.getString(cursor.getColumnIndex(COL_NOMINAL_DETAIL))
                detail_reimbursement.tgl = cursor.getString(cursor.getColumnIndex(COL_TANGGAL_DETAIL))
                detail_reimbursement.src = cursor.getString(cursor.getColumnIndex(COL_SRC_DETAIL))
                detail_reimbursement.fk = cursor.getInt(cursor.getColumnIndex(COL_FK_REIMBURS))

                lstDetailReimburs.add(detail_reimbursement)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstDetailReimburs
    }

    fun addDetailReimburs(detail_reimbursement: M_detailReimbusment)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_KEPERLUAN_DETAIL,detail_reimbursement.keperluan)
        values.put(COL_MILIK_DETAIL,detail_reimbursement.milik)
        values.put(COL_NOMINAL_DETAIL,detail_reimbursement.nominal)
        values.put(COL_TANGGAL_DETAIL,detail_reimbursement.tgl)
        values.put(COL_SRC_DETAIL,detail_reimbursement.src)
        values.put(COL_FK_REIMBURS,detail_reimbursement.fk)
        db.insert(TABLE_DETAIL,null,values)
        db.close()
    }

    fun updateDetailReimburs(detail_reimbursement: M_detailReimbusment):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_KEPERLUAN_DETAIL,detail_reimbursement.keperluan)
        values.put(COL_MILIK_DETAIL,detail_reimbursement.milik)
        values.put(COL_NOMINAL_DETAIL,detail_reimbursement.nominal)
        values.put(COL_TANGGAL_DETAIL,detail_reimbursement.tgl)
        values.put(COL_SRC_DETAIL,detail_reimbursement.src)
        values.put(COL_FK_REIMBURS,detail_reimbursement.fk)

        return db.update(TABLE_DETAIL, values,"$COL_ID_DETAIL=?", arrayOf(detail_reimbursement.id.toString()))
    }

    fun deleteDetailReimburs(detail_reimbursement: M_detailReimbusment)
    {
        val db = this.writableDatabase
        db.delete(TABLE_DETAIL,"$COL_ID_DETAIL=?", arrayOf(detail_reimbursement.id.toString()))
        db.close()
    }
    //CRUD DetailReimbusment b
}