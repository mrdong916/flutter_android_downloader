
# Flutter Android Downloader

[![Pub](https://img.shields.io/pub/v/flutter_android_downloader.svg?style=flat-square)](https://pub.dartlang.org/packages/flutter_android_downloader)


flutter_android_downloader 一个调用安卓系统下载管理器的插件

## 添加依赖

```yaml
dependencies:
  flutter_android_downloader: ^0.0.1+2
```

## 创建下载

```dart
FlutterAndroidDownloader.download("url", "path", "fileName");
```

> 参数说明

- url: 下载地址

- path: 下载路径，目前只能保存在SD卡目下，比如保存到SD卡`A`根目录就填写`/A`

- fileName: 保存的文件名称

## 使用的示例

```dart
import 'package:flutter/material.dart';
import 'package:flutter_android_downloader/flutter_android_downloader.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  void initState() {
    super.initState();
    FlutterAndroidDownloader.getPermission();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: IconButton(
            icon: Icon(Icons.file_download),
            onPressed: () {
              FlutterAndroidDownloader.download(
                  "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
                  "/ABC",
                  "mobileqq_android.apk");
            },
          ),
        ),
      ),
    );
  }
}

```

