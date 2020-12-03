//
// import 'dart:async';
//
// import 'package:flutter/services.dart';
//
//
// class FlutterPlugin {
//   static const MethodChannel _channel =
//       const MethodChannel('flutter_plugin');
//
//   static Future<String> get platformVersion async {
//     final String version = await _channel.invokeMethod('getPlatformVersion');
//     return version;
//   }
// }

library flutter_plugin;

export 'package:flutter_plugin/utils/z_log.dart';
export 'package:flutter_plugin/utils/z_device_info.dart';
export 'package:flutter_plugin/utils/z_system_calendar.dart';