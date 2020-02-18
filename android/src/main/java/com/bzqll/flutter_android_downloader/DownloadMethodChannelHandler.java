package com.bzqll.flutter_android_downloader;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

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
            case "Download":
                Long downloadId = null;
                String url = call.argument("url");
                String fileName = call.argument("fileName");
                String directory = call.argument("directory");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        this.activity.requestPermissions(permissions, PERMISSION_CODE);

                    } else {
                        downloadId = startDownload(url,fileName,directory);
                    }
                } else {
                    downloadId = startDownload(url,fileName,directory);
                }
                result.success(downloadId);
                break;
        }
    }
    private long startDownload(String url,String fileName,String directory) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();
        Environment.getExternalStoragePublicDirectory(directory).mkdir();
        request.setDestinationInExternalPublicDir(directory, fileName);
        // 设置下载网络类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置Notification的标题和描述
        request.setTitle(fileName);
        request.setDescription("Downloading files");
        //设置Notification的显示，和隐藏。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        return manager.enqueue(request);
    }
}
