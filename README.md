
# Flutter Android Downloader

[![Pub](https://img.shields.io/pub/v/dio.svg?style=flat-square)](https://pub.dartlang.org/packages/flutter_android_downloader)


flutter_android_downloader 一个调用安卓系统下载管理器的插件

## 添加依赖

```yaml
dependencies:
  flutter_android_downloader: ^0.0.1
```
## 使用的示例

```dart

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  void initState(){
    super.initState();
    FlutterAndroidDownloader.getPermission();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: FlatButton(
            child: Text("Download"),
            onPressed: (){
              FlutterAndroidDownloader.download("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk","/storage/emulated/0/Download/","mobileqq_android.apk");
            },
          ),
        ),
      ),
    );
  }
}
```

