import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAndroidDownloader {
  static const MethodChannel _channel =
      const MethodChannel('flutter_android_downloader');

  static EventChannel _eventChannel =
      const EventChannel('flutter_android_downloader/complete');
  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<void> getPermission() async {
    await _channel.invokeMethod('getPermission');
  }

  static Stream _streamSubscription = const Stream.empty();

  static Future<int> download(String url, String directory, String fileName,
      String originName, Map<String, String> headers) async {
    return await _channel.invokeMethod('download', {
      'url': url,
      'directory': directory,
      'fileName': fileName,
      'originName': originName,
      'headers': headers,
    });
  }

  static void listen(Function callback) {
    // 当页面生成的时候就开始监听数据的变化
    _streamSubscription = _eventChannel.receiveBroadcastStream();
    _streamSubscription.listen((data) {
      callback(data);
    });
  }
}
