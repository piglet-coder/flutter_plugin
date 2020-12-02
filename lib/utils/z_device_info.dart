import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/constants.dart';

/// @author zdl
/// date 2020/11/26 17:08
/// email zdl328465042@163.com
/// description 设备信息通道
class ZDeviceInfo {
  ZDeviceInfo._();

  static var _channel;

  static _getChannel() {
    if (Platform.isAndroid) {
      return _channel ?? MethodChannel(channelNameDeviceDetail);
    } else {
      return null;
    }
  }

  /// 动态生成的uuid，卸载重装会改变
  static Future<String> get uuid async {
    return await _getChannel()?.invokeMethod('getUuid');
  }

  /// android.os.Build.FINGERPRINT 设备的唯一标识。由设备的多个信息拼接合成
  static Future<String> get fingerprint async {
    return await _getChannel()?.invokeMethod('getFingerprint');
  }
}
