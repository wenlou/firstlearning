package com.example.httplearing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button send,send2,parse_xmlwithpull;
    private TextView response_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send= (Button) findViewById(R.id.send);
        send2= (Button) findViewById(R.id.send2);
        parse_xmlwithpull= (Button) findViewById(R.id.parse_xmlwithpull);
        response_text= (TextView) findViewById(R.id.response_text);
        send.setOnClickListener(this);
        send2.setOnClickListener(this);
        parse_xmlwithpull.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String address="http://www.baidu.com";
        final String xmladdress="http://127.0.0.1:8080/webDemo_war_exploded/get_data.xml";
        if(v.getId()==R.id.send){
           HTTPUtil.sendRequsetWithHttpURL(address, new HTTPUtil.HttpbackListenr() {
                @Override
                public void onFinsh(String response) {
                    showResponse(response.toString());
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        if(v.getId()==R.id.send2){
            HTTPUtil.sendRequsetWithOKHttp(address,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String requstDta=response.body().string();
                    showResponse(requstDta);

                }
            });
        }
        if(v.getId()==R.id.parse_xmlwithpull){
            HTTPUtil.sendRequsetWithOKHttp(xmladdress,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String requstDta=response.body().string();
                    parseXMLWithPull(requstDta);

                }
            });
        }

    }

    /**
     * 利用JsonObject解析Json
     * @param jsonData
     */
    private void parseJSONWithJSONPbject(String jsonData){
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                String version=jsonObject.getString("version");
                Log.d("MainActivty","id is"+id);
                Log.d("MainActivty","name is"+name);
                Log.d("MainActivty","version is"+version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用Gson解析。当解析的对象不是数组的时候仅需传入（jsonData,.class）即可
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData){
        try {
           Gson gson=new Gson();
            List<App> applist=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
            for(App app:applist){
                Log.d("MainActivty","id is"+app.getId());
                Log.d("MainActivty","name is"+app.getName());
                Log.d("MainActivty","version is"+app.getVersion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String requstDta) {
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(requstDta));
            int eventType=xmlPullParser.getEventType();
            Log.d("MainActivty","id is"+eventType+"  ");
            String id="";
            String name="";
            String version="";
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("MainActivty","id is"+id);
                            Log.d("MainActivty","name is"+name);
                            Log.d("MainActivty","version is"+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                response_text.setText(s);
            }
        });
    }
}
