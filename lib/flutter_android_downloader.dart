import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAndroidDownloader {
  static MethodChannel _methodChannel =
      const MethodChannel('com.bzqll.android_downloader/download');

  static EventChannel _eventChannel =
      const EventChannel('com.bzqll.android_downloader/complete');

  static Stream _streamSubscription;

  static Future<int> download(String url, String directory, String fileName,
      String originName, Map<String, String> headers) async {
    return await _methodChannel.invokeMethod<int>('download', {
      'url': url,
      'directory': directory,
      'fileName': fileName,
      'originName': originName,
      'headers': headers ?? {}
    });
  }

  static Future<String> get platformVersion async {
    return await _methodChannel.invokeMethod<String>('getPlatformVersion');
  }

  static Future<void> getPermission() async {
    await _methodChannel.invokeMethod('getPermission');
  }

  static void listen(Function callback) {
    // 当页面生成的时候就开始监听数据的变化
    _streamSubscription = _eventChannel.receiveBroadcastStream();
    _streamSubscription.listen((data) {
      callback(data);
    });
  }
}
