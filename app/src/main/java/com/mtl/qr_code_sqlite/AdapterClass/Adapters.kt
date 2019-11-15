package com.example.testapp.AdapterClass

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Database.DBHanlder
import com.mtl.qr_code_sqlite.Database.Notepad
import com.mtl.qr_code_sqlite.R
import kotlinx.android.synthetic.main.item_list.view.*


class NotepadAdapter (mCtx:Context, val NotepadmodelCls:ArrayList<Notepad> ): RecyclerView.Adapter<NotepadAdapter.ViewHolder>(){



    val mCtx=mCtx



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotepadAdapter.ViewHolder {


        val view=  LayoutInflater.from(mCtx).inflate(R.layout.item_list,parent,false)


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return NotepadmodelCls.size

    }

    override fun onBindViewHolder(holder: NotepadAdapter.ViewHolder, position: Int) {


         val notepad:Notepad = NotepadmodelCls[position]
        holder.title.text=notepad.title
        holder.description.text=notepad.description
        holder.idNumber.text= notepad.id.toString()
        holder.delete.setOnClickListener {


            val title=notepad.title

           var alertdialog= AlertDialog.Builder(mCtx)
               .setTitle("Warning")
               .setMessage("Äre You Sure To Delete $title")
               .setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->


                   val dbHanlder = DBHanlder(mCtx, null, null, 1)


                   if (dbHanlder.deleteNote(notepad.id)){
                       NotepadmodelCls.removeAt(position)

                   }

                   else{

                       notifyItemRemoved(position)
                       notifyItemChanged(position)
                       Toast.makeText(mCtx,"Deleted Node $title",Toast.LENGTH_SHORT).show()
                   }






               }).setNegativeButton("NO" ,DialogInterface.OnClickListener { dialogInterface, i ->  }).show()

        }





    }



    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){



         val title=itemView.txvTitle
        val description=itemView.descriptionText
        val idNumber=itemView.idNo
        val delete=itemView.btnDelete



    }

}