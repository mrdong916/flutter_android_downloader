package com.bzqll.flutter_android_downloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import io.flutter.plugin.common.EventChannel;
import static android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE;

public class DownloadBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
    private final Context context;
    private final DownloadManager downloadManager;
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
