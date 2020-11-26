package com.piglet_coder.flutter_plugin.impl

import android.util.Log
import com.piglet_coder.flutter_plugin.BuildConfig
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @author zdl
 * date 2020/11/24 18:48
 * email zdl328465042@163.com
 * description log实现类
 */
class ZLogImpl: MethodChannel.MethodCallHandler {
    private val methodBase = "ZLogMethod"

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (!BuildConfig.DEBUG) return result.success("")

        val tag = call.argument<String>("tag")
        val msg = call.argument<String>("msg") ?: ""
        //通过methodCall可以获取参数和方法名  执行对应的平台业务逻辑即可
        when (call.method) {
            "${methodBase}1" -> {
                Log.d(tag, msg)
                result.success("")
            }
            "${methodBase}2" -> {
                Log.i(tag, msg)
                result.success("")
            }
            "${methodBase}3" -> {
                Log.w(tag, msg)
                result.success("")
            }
            "${methodBase}4" -> {
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