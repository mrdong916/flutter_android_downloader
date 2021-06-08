# Flutter Android Downloader

[![Pub](https://img.shields.io/pub/v/flutter_android_downloader.svg?style=flat-square)](https://pub.dartlang.org/packages/flutter_android_downloader)


flutter_android_downloader 一个调用安卓系统下载管理器的插件

## 添加依赖

```yaml
dependencies:
  flutter_android_downloader: ^0.0.4
```

## 创建下载

```dart
int downloadId = FlutterAndroidDownloader.download("url", "path", "fileName","originName","originName");
```

> 参数说明

| 参数 | 参数类型 |说明 |
| :-----| :-----| :-----|
| url | String | 下载地址 |
| path | String | 下载路径,Android10及以上默认下载到内置存储目录的Download文件夹 |
| fileName | String | 保存的文件名称 |
| originName | String | 下载来源名称 |
| headers | Map<String,String> |请求头,参数可选 |

## 监听下载完成回调信息
```dart
FlutterAndroidDownloader.listen((id) {
  print("success $id");
  // to do something
});
```

## 完整使用示例

```dart
FlutterAndroidDownloader.listen((id) {
  print("success $id");
});

void download() async {
    int id = await FlutterAndroidDownloader.download(
        "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
        "/ABC",
        "qq.apk",
        "QQ",
        {}
    );
    print("ID => $id");
}

```
