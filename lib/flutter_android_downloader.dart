import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAndroidDownloader {
  static const MethodChannel _channel =
      const MethodChannel('flutter_android_downloader');

  static StreamSubscription streamSubscription;
  static const EventChannel _eventChannel =
  EventChannel("flutter_android_downloader/downloadComplete");

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<int> download(String url, String directory,
      String fileName) async {
    return await _channel.invokeMethod(
        'Download', {"url": url, "directory": directory, "fileName": fileName});
  }

  static listen(Function callback) {
    // 当页面生成的时候就开始监听数据的变化
    streamSubscription = _eventChannel.receiveBroadcastStream().listen(
          (data) {
        callback(data);
      },
      cancelOnError: true,
    );
  }
}
