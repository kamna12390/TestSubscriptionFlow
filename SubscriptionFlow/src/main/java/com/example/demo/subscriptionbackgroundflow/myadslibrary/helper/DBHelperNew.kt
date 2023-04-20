package com.example.demo.subscriptionbackgroundflow.myadslibrary.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperNew(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        // TODO Auto-generated method stub
        db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS $moreApps (id integer primary key, index_no number,name text,thumb text,link text)"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $moreApps")
        onCreate(db)
    }

    fun insertPath(index: Int, name: String, thumb: String, link: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("index_no", index)
        contentValues.put("name", name)
        contentValues.put("thumb", thumb)
        contentValues.put("link", link)
//        if (!checkPathExist(path)) {
        db.insert(moreApps, null, contentValues)
//        }
        return true
    }

    fun deleteData() {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.execSQL("delete from " + moreApps);
        db.close();
    }


    fun updateData(index: Int, name: String, thumb: String, link: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("index_no", index)
        contentValues.put("name", name)
        contentValues.put("thumb", thumb)
        contentValues.put("link", link)

        return db.update(moreApps, contentValues, "index_no = ?", arrayOf(index.toString()))
    }

    val allMoreAppData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $moreApps", null)
        }


    companion object {
        const val DATABASE_NAME = "MyDBMore.db"
        const val moreApps = "moreapps"
        const val CONTACTS_COLUMN_INDEX = "index_no"
        const val CONTACTS_COLUMN_NAME = "name"
        const val CONTACTS_COLUMN_THUMB = "thumb"
        const val CONTACTS_COLUMN_LINK = "link"
    }
}