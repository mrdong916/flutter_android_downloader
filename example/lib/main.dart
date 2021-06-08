import 'package:flutter/material.dart';
import 'package:flutter_android_downloader/flutter_android_downloader.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    FlutterAndroidDownloader.listen((data) {
      print("success $data");
      // to do something
    });
  }

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

  void download() async {
    int id = await FlutterAndroidDownloader.download(
      "https://dl.hdslb.com/mobile/latest/iBiliPlayer-bili.apk?t=1582264529000",
      "/storage/emulated/0/Download",
      "bilibili.apk",
      'test',
      {},
    );
    print("ID => $id");
  }
}
