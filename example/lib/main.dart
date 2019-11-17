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
