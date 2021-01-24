package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText input ;
    private TextView showdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputUser);
        showdata = findViewById(R.id.showUsers);

    }

    public  void add(View view) {
        ContentValues values = new ContentValues();

        String txt = input.getText().toString();
        values.put(MyContentProvider.name , txt);

        getContentResolver().insert(MyContentProvider.CONTENT_URI , values);

        Toast.makeText(this , " USer added" , Toast.LENGTH_LONG).show();
    }

    public  void read (View view){

        Cursor c = getContentResolver().query(Uri.parse("content://com.example.provider/users") , null , null , null , null);
        StringBuilder data = new StringBuilder();
        while (c.moveToNext()){


            data.append( c.getString(c.getColumnIndex("id")))
                .append(" ")
                .append(c.getString(c.getColumnIndex("name")))
                .append("\n");

        }


        if (data.equals("")){
            showdata.setText("No data found");
        }else {
            showdata.setText(data);
        }
    }


}