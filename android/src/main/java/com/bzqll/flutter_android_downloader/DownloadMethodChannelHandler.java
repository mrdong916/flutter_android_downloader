package com.bzqll.flutter_android_downloader;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class DownloadMethodChannelHandler implements MethodChannel.MethodCallHandler  {
    private Context context;
    private DownloadManager manager;
    private Activity activity;

    DownloadMethodChannelHandler(Context context, DownloadManager manager, Activity activity) {
        this.context = context;
        this.manager = manager;
        this.activity = activity;
    }

    @Override
    public void onMethodCall( MethodCall call,  MethodChannel.Result result) {
        int PERMISSION_CODE = 1000;
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + Build.VERSION.RELEASE);
                break;
            case "getPermission":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (this.activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        this.activity.requestPermissions(permissions, PERMISSION_CODE);
                    }
                }
                break;
            case "download":
                Long downloadId = null;
                String url = call.argument("url");
                String fileName = call.argument("fileName");
                String directory = call.argument("directory");
                String originName = call.argument("originName");
                Map<String,String> headers = call.argument("headers");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        this.activity.requestPermissions(permissions, PERMISSION_CODE);

                    } else {
                        downloadId = startDownload(url,fileName,directory,originName,headers);
                    }
                } else {
                    downloadId = startDownload(url,fileName,directory,originName,headers);
                }
                result.success(downloadId);
                break;
        }
    }
    private long startDownload(String url, String fileName, String directory, String originName, Map<String, String> headers) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        if(headers!=null){
            for (String key:headers.keySet()){
                request.addRequestHeader(key,headers.get(key));
            }
        }
        request.setDestinationInExternalPublicDir(directory, fileName);
        request.setTitle(fileName);
        request.setAllowedOverRoaming(true);
        request.setDescription(originName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //DownloadManager manager = (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);
        //return manager.enqueue(request);
        return manager.enqueue(request);
    }
}
