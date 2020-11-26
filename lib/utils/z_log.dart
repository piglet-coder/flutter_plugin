import 'dart:developer';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/constants.dart';

/// @author zdl
/// date 2020/11/25 10:06
/// email zdl328465042@163.com
/// description log工具类
class ZLog {
  ZLog._();

  static var _channel;

  static _getChannel() {
    if (Platform.isAndroid) {
      return _channel ?? MethodChannel(channelNameLog);
    } else {
      return null;
    }
  }

  static void i(
    String msg, {
    String tag,
  }) {
    _log(tag, msg, _ZLogLevel.i);
  }

  static void d(
    String msg, {
    String tag,
  }) {
    _log(tag, msg, _ZLogLevel.d);
  }

  static void v(
    String msg, {
    String tag,
  }) {
    _log(tag, msg, _ZLogLevel.v);
  }

  static void w(
    String msg, {
    String tag,
  }) {
    _log(tag, msg, _ZLogLevel.w);
  }

  static void e(
    String msg, {
    String tag,
  }) {
    _log(tag, msg, _ZLogLevel.e);
  }

  static void _log(String tag, String msg, _ZLogLevel level) {
    tag = 'Flutter_${tag ?? ''}  ';
    if (Platform.isAndroid) {
      if (msg.length > 4000) {
        log(msg, name: tag);
        msg = 'msg长度大于4000，请查看Run打印，原${level.toString()}';
        level = _ZLogLevel.e;
      }
      var map = {
        'tag': tag,
        'msg': msg,
      };
      _getChannel()?.invokeMethod('ZLogMethod${level.index}', map);
    } else {
      print('$tag $msg');
    }
  }
}

/// 这里的顺序不能更改，否则将导致原生log中level等级混乱
enum _ZLogLevel {
  v,
  d,
  i,
  w,
  e,
}
