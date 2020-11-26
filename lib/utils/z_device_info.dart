import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/constants.dart';

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

  /// 不同版本获取方式不同，未做适配，不推荐使用
  static Future<String> get macAddress async {
    return await _getChannel()?.invokeMethod('getMac');
  }

  /// android.os.Build.FINGERPRINT 设备的唯一标识。由设备的多个信息拼接合成
  static Future<String> get fingerprint async {
    return await _getChannel()?.invokeMethod('getFingerprint');
  }
}
