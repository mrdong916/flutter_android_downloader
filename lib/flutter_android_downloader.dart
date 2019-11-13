import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAndroidDownloader {
  static const MethodChannel _channel =
      const MethodChannel('flutter_android_downloader');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static download(String url, String directory, String fileName) async {
    await _channel.invokeMethod(
        'Download', {"url": url, "directory": directory, "fileName": fileName});
  }

  static getPermission() async {
    await _channel.invokeMethod('getPermission');
  }
}
