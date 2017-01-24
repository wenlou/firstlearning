package com.example.broadcastbest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxj52 on 2017/1/17.
 */

public class AcitvtyCollerctor {
    public static List<Activity> mActivities=new ArrayList<Activity>();
    public static void addActivty(Activity activity){
        mActivities.add(activity);
    }
    public static void removeActivty(Activity activity){
        mActivities.remove(activity);
    }
    public  static void finshAll(){
        for(Activity activity:mActivities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
