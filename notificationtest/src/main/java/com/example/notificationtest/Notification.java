package com.example.notificationtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Notification extends AppCompatActivity {
    public static final int TAKE_PHOTO=1;
    public static final int Choose_PHOTO=2;
    private Button take_photo,chooose_photo;
    private ImageView picture;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        take_photo= (Button) findViewById(R.id.take_photo);
        picture= (ImageView) findViewById(R.id.picture);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try {
                if(outputImage.exists()){
                    outputImage.delete();
                }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    ImageUri= FileProvider.getUriForFile(Notification.this,"com.example.notificationtest.fileprovider",outputImage);
                }
                else{
                    ImageUri=Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        chooose_photo= (Button) findViewById(R.id.chose_photo);
        chooose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Notification.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });
    }

    private void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //打开相册
        startActivityForResult(intent,Choose_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case TAKE_PHOTO:
           if(resultCode==RESULT_OK){
               try {
                   //将拍摄的照片显示出来
                   Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                   picture.setImageBitmap(bitmap);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
           }
           break;
           case Choose_PHOTO:
               if(resultCode==RESULT_OK){
                   //判断手机系统版本号
                   if(Build.VERSION.SDK_INT>=19){
                       //4.4及以上用这个方法处理
                       handleImageOnKitKat(data);
                   }else{
                       handleImageBeforKitKat(data);
                   }
               }
               break;
        default:
            break;
       }
    }
//动态获取权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else{
                    Toast.makeText(this,"You denide the permission",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void handleImageBeforKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);

    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docid=DocumentsContract.getDocumentId(uri);
            //如果是doucuments类型的url，则通过 docid处理
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docid.split(":")[1];//解析出数字格式的id
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if("com.android.providers.media.downloads.document".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downlodas//public_downloads"),Long.valueOf(docid));
                imagePath=getImagePath(contentUri,null);
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //如果是content类型的则用普通模式
                imagePath=getImagePath(uri,null);
            }
            else if("file".equalsIgnoreCase(uri.getScheme())){
                //如果是file，则直接获取路径显示图片
                imagePath=uri.getPath();
            }
            displayImage(imagePath);
        }

    }

    private void displayImage(String imagePath) {
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Notification.this,"获取图片错误",Toast.LENGTH_LONG).show();
        }
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path=null;
        //通过url和selection获取真实路径
        Cursor cursor=getContentResolver().query(externalContentUri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
