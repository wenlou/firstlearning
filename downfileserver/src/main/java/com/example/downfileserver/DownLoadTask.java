package com.example.downfileserver;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sxj52 on 2017/1/26.
 */

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;
    private DowmloadListener mListener;
    private boolean isCanceled=false;
    private boolean isPaused;
    private int lastProgress;
    public DownLoadTask(DowmloadListener listener){
        this.mListener=listener;
    }


    @Override
    protected Integer doInBackground(String... params) {
        InputStream is=null;
        RandomAccessFile savedFile=null;
        File file=null;
        try {
           long downloadLength=0;//记录已下载文件长度
           String downloadUrl=params[0];
           String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
           String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
           file=new File(directory+fileName);
           if(file.exists()){
              downloadLength=file.length();
            }
            long contentLength=getContentLength(downloadUrl);
            if(contentLength==0){
                return TYPE_FAILED;
            }else if(contentLength==downloadLength){
                //已经下载的文件长度和文件总长度相等，说明下载完成
                return TYPE_SUCCESS;
            }
            OkHttpClient client=new OkHttpClient();
            //断点下载，指定从哪个字节开始
            Request request=new Request.Builder().addHeader("RANGE","bytes="+downloadLength+"-")
                    .url(downloadUrl).build();
            Response response=client.newCall(request).execute();
            if(response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已下载字节
                byte[] b=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(b))!=-1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else{
                        total=len;
                        savedFile.write(b,0,len);
                        //计算已下载百分比
                        int progress= (int) ((total+downloadLength)*100/contentLength);
                        publishProgress(progress);
                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
                if(savedFile!=null){
                    savedFile.close();
                }
                if(isCanceled&&file!=null){
                    file.delete();
                }
            }
            catch (Exception e) {
                    e.printStackTrace();
                }

        }
        return TYPE_FAILED;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(downloadUrl).build();
        Response response=client.newCall(request).execute();
        if(response!=null&&response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
    public void pauseDownload(){
        isPaused=true;
    }
    public void cancelDownload(){
        isCanceled=true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
       int progress=values[0];
        if(progress>lastProgress){
            mListener.onProgress(progress);
            lastProgress=progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
       switch (integer){
           case TYPE_SUCCESS:
               mListener.onSuccess();
               break;
           case TYPE_FAILED:
               mListener.OnFailed();
               break;
           case TYPE_PAUSED:
               mListener.onPaused();
               break;
           case TYPE_CANCELED:
               mListener.onCanceled();
               break;
           default:
               break;
       }
    }
}
