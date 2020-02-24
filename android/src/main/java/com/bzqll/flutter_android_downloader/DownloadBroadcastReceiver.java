package com.bzqll.flutter_android_downloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.flutter.plugin.common.EventChannel;

import static android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE;

public class DownloadBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
    private Context context;
    private DownloadManager downloadManager;
    private EventChannel.EventSink events;

    public DownloadBroadcastReceiver(Context context,DownloadManager downloadManager) {
        this.context = context;
        this.downloadManager = downloadManager;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.events = events;
        context.registerReceiver(this, new IntentFilter(ACTION_DOWNLOAD_COMPLETE));
    }
    @Override
    public void onCancel(Object arguments) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (events != null) {
            if(intent.getAction()!=null && intent.getAction().equals(ACTION_DOWNLOAD_COMPLETE)){
                // 下载完成后发送给flutter下载完成的ID
                long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
                events.success(completeDownloadId);
            }
        }
    }

}
