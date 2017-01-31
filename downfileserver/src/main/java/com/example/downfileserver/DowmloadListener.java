package com.example.downfileserver;

/**
 * Created by sxj52 on 2017/1/26.
 */

public interface DowmloadListener {
      void onProgress(int progress);
      void onSuccess();//成功
     void OnFailed();//失败
     void onPaused();//暂停
    void onCanceled();//取消
}

