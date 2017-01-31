package com.example.mdbest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Fruit[] mFruits={new Fruit("Apple",R.drawable.apple),new Fruit("Banana",R.drawable.banana)
    ,new Fruit("Cherry",R.drawable.cherry),new Fruit("Orange",R.drawable.orange),new Fruit("Pear",R.drawable.pear)
    ,new Fruit("pineapple",R.drawable.pineapple)};

    private ArrayList<Fruit> mList=new ArrayList<Fruit>();
    private FruitAdapter mFruitAdapter;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavigationView= (NavigationView) findViewById(R.id.nav_view);
            ActionBar actionBar=getSupportActionBar();
            if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Delet deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        initFruit();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        mFruitAdapter=new FruitAdapter(mList);
        recyclerView.setAdapter(mFruitAdapter);
        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFriuts();
            }

        });


    }

    private void refreshFriuts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        mFruitAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruit() {
        mList.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(mFruits.length);
            mList.add(mFruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()){
          case android.R.id.home:
              mDrawerLayout.openDrawer(GravityCompat.START);
              break;
          case R.id.backup:
              Toast.makeText(this,"You Clicked Backup",Toast.LENGTH_SHORT).show();
              break;
          case R.id.delete:
              Toast.makeText(this,"You Clicked Deleet",Toast.LENGTH_SHORT).show();
              break;
          case R.id.settings:
              Toast.makeText(this,"You Clicked Setting",Toast.LENGTH_SHORT).show();
              break;
      }
        return true;
    }
}
