package com.piglet_coder.flutter_plugin.utils

import android.util.Log
import com.piglet_coder.flutter_plugin.BuildConfig
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @author zdl
 * date 2020/11/24 18:48
 * email zdl328465042@163.com
 * description
 */
class ZLogImpl: MethodChannel.MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (!BuildConfig.DEBUG) return result.success("")

        val tag = call.argument<String>("tag")
        val msg = call.argument<String>("msg") ?: ""
        //通过methodCall可以获取参数和方法名  执行对应的平台业务逻辑即可
        when (call.method) {
            "ZLogUtil1" -> {
                Log.d(tag, msg)
                result.success("")
            }
            "ZLogUtil2" -> {
                Log.i(tag, msg)
                result.success("")
            }
            "ZLogUtil3" -> {
                Log.w(tag, msg)
                result.success("")
            }
            "ZLogUtil4" -> {
                Log.e(tag, msg)
                result.success("")
            }
            else -> {
                Log.v(tag, msg)
                result.success("")
            }
        }
    }
}