package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button add_button;
    private Button query_data;
    private Button updata_data;
    private Button delete_data;
    private String newID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_button= (Button) findViewById(R.id.add_data);
        query_data= (Button) findViewById(R.id.query_data);
        updata_data= (Button) findViewById(R.id.updata_data);
        delete_data= (Button) findViewById(R.id.delet_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.databasetest.provider/book");
                ContentValues values=new ContentValues();
                values.put("name","2222");
                values.put("author","4444");
                values.put("price",23);
                values.put("pages",467);
                Uri newUri=getContentResolver().insert(uri,values);
                newID=newUri.getPathSegments().get(1);
            }
        });
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.databasetest.provider/book");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivty","book name"+name);
                        Log.d("MainActivty","book name"+author);
                        Log.d("MainActivty","book name"+pages);
                        Log.d("MainActivty","book name"+price);
                    }
                }
            }
        });
    }
}
