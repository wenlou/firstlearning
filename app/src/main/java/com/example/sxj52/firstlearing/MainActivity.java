package com.example.sxj52.firstlearing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private List<Fruit> mFruitList=new ArrayList<Fruit>();
      private List<Msg> mMsgs=new ArrayList<Msg>();
      private EditText msg_text;
      private Button msg_send;
      private  RecyclerView recyclerView;
       private MsgAdpter msgAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        recyclerView= (RecyclerView) findViewById(R.id.recyxlerview);
        msg_text= (EditText) findViewById(R.id.msg_text);
        msg_send= (Button) findViewById(R.id.msg_send);
        LinearLayoutManager layoutmanager=new LinearLayoutManager(this);
        //改成垂直排布
//        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //瀑布流
       // StaggeredGridLayoutManager layoutmanager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        //FruitAdapter fruitAdpter=new FruitAdapter(mFruitList);
        msgAdpter=new MsgAdpter(mMsgs);
        recyclerView.setAdapter(msgAdpter);
        msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contx=msg_text.getText().toString();
                if(!"".equals(contx)){
                    Msg msg=new Msg(contx,Msg.TYPE_RE);
                    mMsgs.add(msg);
                    //刷新recyclerView显示
                    msgAdpter.notifyItemChanged(mMsgs.size()-1);
                    //将recyclerView定位到最后一行
                    recyclerView.scrollToPosition(mMsgs.size()-1);
                    msg_text.setText("");
                }
            }
        });

    }

    private void initFruits() {
        Msg msg1=new Msg("你好",Msg.TYPE_RE);
        mMsgs.add(msg1);
        Msg msg2=new Msg("我不好",Msg.TYPE_CO);
        mMsgs.add(msg2);
        Msg msg3=new Msg("你好",Msg.TYPE_RE);
        mMsgs.add(msg3);
    }

//    private String getRandomLentnName(String s) {
//        Random random=new Random();
//        int length=random.nextInt(20)+1;
//        StringBuilder builder=new StringBuilder();
//        for (int i=0;i<length;i++){
//            builder.append(i);
//        }
//        return builder.toString();
//    }
}
