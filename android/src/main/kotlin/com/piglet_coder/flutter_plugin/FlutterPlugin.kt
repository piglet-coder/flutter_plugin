package com.piglet_coder.flutter_plugin

import androidx.annotation.NonNull
import com.piglet_coder.flutter_plugin.utils.ZLogImpl

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

/** FlutterPlugin */
class FlutterPlugin : FlutterPlugin {
    private lateinit var channelLog: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channelLog = MethodChannel(flutterPluginBinding.binaryMessenger, Constants.channelNameLog)
        channelLog.setMethodCallHandler(ZLogImpl())
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channelLog.setMethodCallHandler(null)
    }
}
