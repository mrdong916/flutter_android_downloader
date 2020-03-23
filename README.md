
# Flutter Android Downloader

[![Pub](https://img.shields.io/pub/v/flutter_android_downloader.svg?style=flat-square)](https://pub.dartlang.org/packages/flutter_android_downloader)


flutter_android_downloader 一个调用安卓系统下载管理器的插件

## 添加依赖

```yaml
dependencies:
  flutter_android_downloader: ^0.0.3
```

## 创建下载

```dart
int downloadId = FlutterAndroidDownloader.download("url", "path", "fileName"，"originName");
```

> 参数说明

- url: 下载地址

- path: 下载路径，目前只能保存在SD卡目下，比如保存到SD卡`A`根目录就填写`/A`

- fileName: 保存的文件名称

- originName: 下载来源名称

## 监听下载完成回调信息
```dart
FlutterAndroidDownloader.listen((data) {
  print("success $data");
  // to do something
});
```

> 具体返回参数信息参考 [DownloadManager API](https://developer.android.com/reference/android/app/DownloadManager.html#COLUMN_STATUS)

## 完成使用的示例

```dart
import 'package:flutter/material.dart';
import 'package:flutter_android_downloader/flutter_android_downloader.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: IconButton(
            icon: Icon(Icons.file_download),
            onPressed: () {
              download();
            },
          ),
        ),
      ),
    );
  }

  void initState() {
    super.initState();
    FlutterAndroidDownloader.listen((data) {
      print("success $data");
      // to do something
    });
  }

  void download() async {
    int id = await FlutterAndroidDownloader.download(
        "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
        "/ABC",
        "qq.apk"，
        "QQ");
    print("ID => $id");

    /// to do something
  }
}

```

