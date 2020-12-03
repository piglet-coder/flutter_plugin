package com.piglet_coder.flutter_plugin

import android.util.Log
import androidx.annotation.NonNull
import com.piglet_coder.flutter_plugin.impl.ZDeviceDetailImpl
import com.piglet_coder.flutter_plugin.impl.ZLogImpl
import com.piglet_coder.flutter_plugin.impl.ZSystemCalendarImpl

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

/** FlutterPlugin */
class FlutterPlugin : FlutterPlugin {
    private lateinit var channelLog: MethodChannel
    private lateinit var channelDeviceDetail: MethodChannel
    private lateinit var channelSystemCalendar: MethodChannel

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channelLog = MethodChannel(binding.binaryMessenger, Constants.channelNameLog)
        channelLog.setMethodCallHandler(ZLogImpl())
        channelDeviceDetail = MethodChannel(binding.binaryMessenger, Constants.channelNameDeviceDetail)
        channelDeviceDetail.setMethodCallHandler(ZDeviceDetailImpl(binding.applicationContext))
        channelSystemCalendar = MethodChannel(binding.binaryMessenger, Constants.channelNameSystemCalendar)
        channelSystemCalendar.setMethodCallHandler(ZSystemCalendarImpl(binding.applicationContext))
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channelLog.setMethodCallHandler(null)
        channelDeviceDetail.setMethodCallHandler(null)
        channelSystemCalendar.setMethodCallHandler(null)
    }
}
