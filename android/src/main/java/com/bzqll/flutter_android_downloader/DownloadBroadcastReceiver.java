package com.bzqll.flutter_android_downloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.EventChannel;

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
        context.registerReceiver(this, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    @Override
    public void onCancel(Object arguments) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (events != null) {
            // 下载完成后发送给flutter下载完成的ID
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            events.success(queryDownloadInfo(completeDownloadId));
        }
    }
    public Map<String,Object> queryDownloadInfo(Long id) {
        Map<String,Object> result = new HashMap<>();
        // 创建一个查询对象
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
        // 根据查询对象获取一个游标对象
        Cursor cursor = this.downloadManager.query(query);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                String uri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                String local_uri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                // String total_size = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                // String media_type = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE));
                // String title = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE));

                result.put("id",id);
                result.put("uri",uri);
                result.put("status",status);
                result.put("local_uri",local_uri);
                // result.put("media_type",media_type);
                // result.put("total_size",total_size);
                // result.put("title",title);
            }
            cursor.close();
        }
        return result;
    }
}
