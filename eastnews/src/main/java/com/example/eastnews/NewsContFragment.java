package com.example.eastnews;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sxj52 on 2017/1/16.
 */

public class NewsContFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.news_content_frag,container,false);
        return mView;
    }
    public void refresh(String newCont,String newTite){
        View visbility=mView.findViewById(R.id.visibility_layout);
        visbility.setVisibility(View.VISIBLE);
        TextView titleText= (TextView) mView.findViewById(R.id.new_title);
        TextView contText= (TextView) mView.findViewById(R.id.news_content);
        titleText.setText(newTite);
        contText.setText(newCont);
    }
}
