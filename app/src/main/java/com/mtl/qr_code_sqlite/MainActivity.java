package com.mtl.qr_code_sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testapp.AdapterClass.NotepadAdapter;
import com.example.testapp.Database.DBHanlder;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mtl.qr_code_sqlite.Database.Notepad;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity  {


    DBHanlder dbHanlder=new DBHanlder(this,null,null,1);
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








//        Notepad notepad= new Notepad();
//        notepad.setTitle("YYY");
//        notepad.setDescription("TTTTTTTTT");


      //  dbHanlder.addNotepad(this,notepad);

        recyclerView=findViewById(R.id.rvView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        Button b1= findViewById(R.id.insert);

        ViewNotes();

        final IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setCameraId(0);  // Use a specific camera of the device

        integrator.setBarcodeImageEnabled(true);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                integrator.initiateScan();

            }
        });
    }

    private void ViewNotes(){
       ArrayList<Notepad> noteList=new ArrayList<>(dbHanlder.getData(this)) ;

        NotepadAdapter notepadAdapter=new NotepadAdapter(this,noteList);

        recyclerView.setAdapter(notepadAdapter);
        notepadAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                Notepad notepad=new Notepad();
                notepad.setTitle(result.getFormatName());
                notepad.setDescription(result.getContents());
                dbHanlder.addNotepad(this,notepad);


                ArrayList<Notepad> noteList=new ArrayList<>(dbHanlder.getData(this)) ;

                NotepadAdapter notepadAdapter=new NotepadAdapter(this,noteList);

                recyclerView.setAdapter(notepadAdapter);
                notepadAdapter.notifyDataSetChanged();
             //   NotepadAdapter notepadAdapter=new NotepadAdapter(this,notepad);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
