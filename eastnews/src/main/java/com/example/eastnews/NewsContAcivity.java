package com.example.eastnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NewsContAcivity extends AppCompatActivity {
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContAcivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_cont", newsContent);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_cont);
        String NewsTitle=getIntent().getStringExtra("news_title");
        String newsCont=getIntent().getStringExtra("news_cont");
        NewsContFragment newsContFragment = (NewsContFragment) getSupportFragmentManager().findFragmentById(R.id.news_cont_fragment);
        newsContFragment.refresh(newsCont,NewsTitle);
    }
}
