package com.example.broadcastbest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sxj52 on 2017/1/17.
 */

public class BaseActivty extends AppCompatActivity {
    private Qing reciver;

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcastbest.qiang");
        reciver=new Qing();
        registerReceiver(reciver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(reciver!=null){
            unregisterReceiver(reciver);
            reciver=null;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcitvtyCollerctor.addActivty(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AcitvtyCollerctor.removeActivty(this);
    }
    class Qing extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
            alertDialog.setTitle("警告").setMessage("你将被强制下线，按警告返回主界面").setCancelable(false).
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AcitvtyCollerctor.finshAll();
                            Intent intent1=new Intent(context,LoginActivity.class);
                            context.startActivity(intent1);
                        }
                    }).show();

        }
    }
}
