package com.example.testapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

import com.mtl.qr_code_sqlite.Database.Notepad
import java.lang.Exception
import java.util.prefs.PreferencesFactory


class DBHanlder (ctx:Context, name:String?, factory:SQLiteDatabase.CursorFactory?, version:Int):
    SQLiteOpenHelper(ctx,DATABASE_NAME,factory,DATABASE_VERSION) {


    companion object{
         private val DATABASE_NAME="Mydata.db"
        private  val DATABASE_VERSION=1


        val TABLE_NAME="NotepadTAble"
        private  val ID="id"
        private val TITLE="title"
        private val DESCRIPTION="description"

    }

    override fun onCreate(db: SQLiteDatabase?) {

         val CREATE_NOTPAD_TABLE= ( "CREATE TABLE $TABLE_NAME (" +
                 " $ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "$TITLE TEXT," +
                 "$DESCRIPTION TEXT DEFAULT 0)" )

         db?.execSQL(CREATE_NOTPAD_TABLE)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    fun getData(mCtx:Context):ArrayList<Notepad>{

        val query="Select * From $TABLE_NAME "
        val db=this.readableDatabase
        val cursor=db.rawQuery(query,null)

        val notepads=ArrayList<Notepad>()

        if (cursor.count==0)
            Toast.makeText(mCtx,"No Data Found",Toast.LENGTH_SHORT).show()

        else {


            while (cursor.moveToNext()) {

                val notepad = Notepad()

                notepad.id = cursor.getInt(cursor.getColumnIndex(ID))
                notepad.title = cursor.getString(cursor.getColumnIndex(TITLE))
                notepad.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))

                notepads.add(notepad)
            }

            Toast.makeText(mCtx,"${cursor.count.toString()}  Records Found ",Toast.LENGTH_SHORT).show()

        }


        cursor.close()
        db.close()
        return notepads


    }

    fun addNotepad(mCtx:Context,notepad:Notepad){


        val values=ContentValues()
        values.put(TITLE,notepad.title)
        values.put(DESCRIPTION,notepad.description)

        val db=this.writableDatabase

        try {

            db.insert(TABLE_NAME,null,values)
            Toast.makeText(mCtx,"Data Inserted",Toast.LENGTH_SHORT).show()
        }

        catch (e:Exception){

            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }

        db.close()

    }

    fun deleteNote(noteID:Int):Boolean{


        val qry="Delete From $TABLE_NAME where $ID = $noteID"
        val db=this.writableDatabase
        var result:Boolean=false

        try {

            val cursor=db.execSQL(qry)
            result=true
        }

        catch (e:Exception){


        }


        db.close()
        return  result

    }

}