package com.bzqll.flutter_android_downloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;


public class FlutterAndroidDownloaderPlugin implements FlutterPlugin , ActivityAware {
  private MethodChannel methodChannel;
  private Activity activity;
  private EventChannel eventChannel;

  @Deprecated public static void registerWith(PluginRegistry.Registrar registrar) {
    FlutterAndroidDownloaderPlugin plugin = new FlutterAndroidDownloaderPlugin();
    plugin.setupChannels(registrar.messenger(), registrar.context(),registrar.activity());

  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    setupChannels(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getApplicationContext(),this.activity);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    teardownChannels();
  }

  private void setupChannels(BinaryMessenger messenger, Context context, Activity activity) {
    methodChannel = new MethodChannel(messenger, "flutter_android_downloader");
    eventChannel = new EventChannel(messenger, "flutter_android_downloader/complete");
    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

    DownloadMethodChannelHandler downloadMethodChannelHandler =
            new DownloadMethodChannelHandler(context,manager,activity);
    DownloadBroadcastReceiver receiver =
            new DownloadBroadcastReceiver(context,manager);
    methodChannel.setMethodCallHandler(downloadMethodChannelHandler);
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
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
  }

  @Override
  public void onDetachedFromActivity() {
  }
}