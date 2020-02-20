package com.bzqll.flutter_android_downloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterAndroidDownloaderPlugin
 */
public class FlutterAndroidDownloaderPlugin implements FlutterPlugin, ActivityAware {

    private MethodChannel methodChannel;
    private EventChannel eventChannel;
    private Activity activity;

    /** Plugin registration. */
    public static void registerWith(Registrar registrar) {
        FlutterAndroidDownloaderPlugin plugin = new FlutterAndroidDownloaderPlugin();
        plugin.setupChannels(registrar.messenger(), registrar.context(),registrar.activity());

    }

    @Override
    public void onAttachedToEngine(FlutterPluginBinding binding) {
        setupChannels(binding.getBinaryMessenger(), binding.getApplicationContext(),this.activity);
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        teardownChannels();
    }

    private void setupChannels(BinaryMessenger messenger, Context context,Activity activity) {
        methodChannel = new MethodChannel(messenger, "com.bzqll.android_downloader/download");
        eventChannel = new EventChannel(messenger, "com.bzqll.android_downloader/complete");
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadMethodChannelHandler methodChannelHandler =
                new DownloadMethodChannelHandler(context,manager,activity);
        DownloadBroadcastReceiver receiver =
                new DownloadBroadcastReceiver(context,manager);

        methodChannel.setMethodCallHandler(methodChannelHandler);
        eventChannel.setStreamHandler(receiver);
    }

    private void teardownChannels() {
        methodChannel.setMethodCallHandler(null);
        eventChannel.setStreamHandler(null);
        methodChannel = null;
        eventChannel = null;
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }
}