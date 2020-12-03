import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/constants.dart';
import 'package:permission_handler/permission_handler.dart';

class ZSystemCalendar {
  const ZSystemCalendar._();

  static var _channel;

  static _getChannel() {
    if (Platform.isAndroid) {
      return _channel ?? MethodChannel(channelNameSystemCalendar);
    } else {
      return null;
    }
  }

  static Future<bool> _checkPermission() async {
    PermissionStatus status = await Permission.calendar.status;
    switch (status) {
      // 从未申请过
      case PermissionStatus.undetermined:
        Permission.calendar.request();
        break;
      // 同意
      case PermissionStatus.granted:
        return true;
        break;
      // 拒绝
      case PermissionStatus.denied:
        Permission.calendar.request();
        break;
      // 仅支持ios
      case PermissionStatus.restricted:
        break;
      // 永久拒绝，仅支持android
      case PermissionStatus.permanentlyDenied:
        break;
    }
    return false;
  }

  /// 向系统日历中添加日程
  /// [reminders] 是否需要提醒，0：不需要，else：提前几分钟提醒
  static Future<bool> addSchedule(
    String title,
    int startTime, {
    int endTime,
    String description,
    int reminderTime = 0,
  }) async {
    //默认持续时间为1个小时
    endTime = startTime + 60 * 60 * 1000;
    var map = {
      'title': title,
      'startTime': startTime,
      'endTime': endTime,
      'description': description,
      'reminderTime': reminderTime,
    };
    bool hasPermission = await _checkPermission();
    return hasPermission
        ? await _getChannel()?.invokeMethod('addSchedule', map)
        : false;
  }

  /// 删除系统日历中的日程
  static Future<bool> delSchedule(String title) async {
    var map = {'title': title};
    bool hasPermission = await _checkPermission();
    return hasPermission
        ? await _getChannel()?.invokeMethod('delSchedule', map)
        : false;
  }
}
