package com.example.downfileserver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;


public class DownloadService extends Service {
    private DownLoadTask mDownLoadTask;
    private String downloadUrl;
    private DowmloadListener mDownloadListener= new DowmloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading....",progress));
        }

        @Override
        public void onSuccess() {
            //下载成功时创建一个成功的通知，并将前台服务关闭
            mDownLoadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Success",-1));

        }

        @Override
        public void OnFailed() {
            //下载失败时创建一个失败的通知，并将前台服务关闭
            mDownLoadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Failed",-1));
        }

        @Override
        public void onPaused() {
            mDownLoadTask=null;

        }

        @Override
        public void onCanceled() {
            mDownLoadTask=null;
            stopForeground(true);

        }
    };
    class DownloadBInder extends Binder{
        public void startDownload(String url){
            if(mDownLoadTask==null){
                downloadUrl=url;
                mDownLoadTask=new DownLoadTask(mDownloadListener);
                mDownLoadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading....",0));
                Toast.makeText(DownloadService.this,"Downloading....",Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if(mDownLoadTask!=null){
                mDownLoadTask.pauseDownload();
            }
        }
        public void cancelDownload(){
            if(mDownLoadTask!=null){
                String filename=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file=new File(directory+filename);
                if(file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Cancel....",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Notification getNotification(String s,int progress) {
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(s);
        if(progress>0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();

    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private DownloadBInder mBinder=new DownloadBInder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
