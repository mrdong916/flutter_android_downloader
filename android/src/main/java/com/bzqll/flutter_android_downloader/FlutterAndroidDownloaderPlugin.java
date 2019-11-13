package com.bzqll.flutter_android_downloader;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterAndroidDownloaderPlugin
 */
public class FlutterAndroidDownloaderPlugin implements MethodCallHandler {

    final int PERMISSION_CODE = 1000;
    String url;
    String fileName;
    String directory;
    Activity activity;

    FlutterAndroidDownloaderPlugin(Activity activity) {
        this.activity = activity;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_android_downloader");
        channel.setMethodCallHandler(new FlutterAndroidDownloaderPlugin(registrar.activity()));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + Build.VERSION.RELEASE);
        } else if (call.method.equals("getPermission")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    activity.requestPermissions(permissions, PERMISSION_CODE);
                }
            }
        } else if (call.method.equals("Download")) {
            url = call.argument("url");
            fileName = call.argument("fileName");
            directory = call.argument("directory");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    activity.requestPermissions(permissions, PERMISSION_CODE);

                } else {
                    startDownloading();
                }
            } else {
                startDownloading();
            }
        }
    }

    public void startDownloading() {
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

        DownloadManager manager = (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }
}
