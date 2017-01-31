package com.example.mdbest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivty extends AppCompatActivity {
    public static  final String FRUIT_NAME="fruit_name";
    public static  final String FRUIT_IMAGE_ID="fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent=getIntent();
        String FriutName=intent.getStringExtra(FRUIT_NAME);
        int FriutImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView= (ImageView) findViewById(R.id.friut_image_view);
        TextView friutTextView= (TextView) findViewById(R.id.friut_content_text);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(FriutName);
        Glide.with(this).load(FriutImageId).into(fruitImageView);
        String fruitContent=generateFriutContrnt(FriutName);
        friutTextView.setText(fruitContent);


    }

    private String generateFriutContrnt(String friutName) {
        StringBuilder fruitContent=new StringBuilder();
        for(int i=0;i<50;i++){
            fruitContent.append(friutName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
              finish();
              return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
