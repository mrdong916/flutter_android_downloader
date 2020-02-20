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
        "/五音",
        "qq.apk");
    print("ID => $id");

    /// to do something
  }
}
