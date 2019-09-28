package com.example.gameapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.Buffer;

import static android.content.ContentValues.TAG;

public class IOUtils {

    private static final int REQUEST_CODE_IO_PERMISSION=0x01;
    public static boolean requestPermission(final Context context){
        if(PermissionChecker.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.CAMERA)) {

                Log.d(TAG, "shouldShowRequestPermissionRationale:追加説明");
                // 権限チェックした結果、持っていない場合はダイアログを出す
                new AlertDialog.Builder(context)
                        .setTitle("パーミッションの追加説明")
                        .setMessage("このアプリはファイルの読み書きにはパーミッションが必要です")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_IO_PERMISSION);
                            }
                        })
                        .create()
                        .show();
                return false;
            }


            // 権限を取得する
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_IO_PERMISSION);

        }
        return true;



    }
    public static void createDir(String dir){
        File f=new File(Environment.getExternalStorageDirectory().getPath(),dir);
        if(!f.exists()) {
            f.mkdir();
        }
    }
    public static String[] getFiles(String dir){
        File f=new File(Environment.getExternalStorageDirectory().getPath(),dir);
        return f.list();

    }
    public static File createPath(String dir,String file){
        String fileName=(null!=dir?dir+"/":"")+file;
        Log.i(IOUtils.class.getName(),String.format("Creating file name.%s",fileName));
        return new File(Environment.getExternalStorageDirectory().getPath(),fileName);
    }

    public static String loadPlainText(String file, AssetManager am)
            throws IOException {

        return loadPlainText(new File(file), am);
    }
    public static String loadPlainText(File file, AssetManager am)
    throws IOException
    {

        BufferedReader br=null;

        try{
            br=new BufferedReader(
                    new InputStreamReader(
                            loadFromStorage(
                                    new File(Environment.getExternalStorageDirectory().getPath(),file.getPath()))));
        }catch(Exception e){
            if(null!=am) {
                br = new BufferedReader(new InputStreamReader(am.open(file.getPath())));
            }else{
                return null;
            }
        }
        String line;
        StringBuilder sb=new StringBuilder();
        while(null!=(line=br.readLine())){
            sb.append(line+"\n");
        }
        return sb.toString();
    }

    public static InputStream loadFromStorage(File targetFile)
            throws IOException{
        Log.i(IOUtils.class.getName(),String.format("%s opened exists=%s",targetFile.getAbsolutePath(),Boolean.toString(targetFile.exists())));
        return (new FileInputStream(targetFile));
    }


    public static BufferedWriter createFile(String file)
            throws IOException{
        File f=new File(Environment.getExternalStorageDirectory(),file);
        return createFile(f);
    }
    public static BufferedWriter createFile(File f)
            throws IOException
    {
        Log.i(IOUtils.class.getName(),String.format("Saved file to %s",f.getAbsolutePath()));
        return new BufferedWriter(new FileWriter(f,false));

    }


}
