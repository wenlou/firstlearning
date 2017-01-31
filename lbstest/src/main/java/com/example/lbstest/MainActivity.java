package com.example.lbstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.TextureMapView;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private TextView position_text_view;
    private TextureMapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        position_text_view= (TextView) findViewById(R.id.position_text_view);
        mLocationClient.start();
        mMapView= (TextureMapView) findViewById(R.id.bmpview);


    }
public class MyLocationListener implements  BDLocationListener{

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        StringBuilder currentPostion=new StringBuilder();
        currentPostion.append("经度:").append(bdLocation.getLatitude()).append("\n");
        currentPostion.append("纬度:").append(bdLocation.getLongitude()).append("\n");
        currentPostion.append("国家:").append(bdLocation.getCountry()).append("\n");
        currentPostion.append("省:").append(bdLocation.getProvince()).append("\n");
        currentPostion.append("市:").append(bdLocation.getCity()).append("\n");
        currentPostion.append("区:").append(bdLocation.getDistrict()).append("\n");
        currentPostion.append("街道:").append(bdLocation.getStreet()).append("\n");
        currentPostion.append("定位方式");
        if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
            currentPostion.append("GPS");
        }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
            currentPostion.append("网络");

        }
        position_text_view.setText(currentPostion);

    }
}

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
