package com.piglet_coder.flutter_plugin.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.*


/**
 * @author zdl
 * date 2020/11/26 14:02
 * email zdl328465042@163.com
 * description 设备详细信息实现类
 */
class ZDeviceDetailImpl(private val ctx: Context): MethodChannel.MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        //通过methodCall可以获取参数和方法名  执行对应的平台业务逻辑即可
        when (call.method) {
            "getUuid" -> {
                result.success(getUuid())
            }
            "getFingerprint" -> {
                result.success(Build.FINGERPRINT)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    //获取uuid
    private fun getUuid(): String{
        val sp: SharedPreferences = ctx.getSharedPreferences("UuidCached", Context.MODE_PRIVATE)
        var uuid = sp.getString("uuid", "")
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString()
            val edit = sp.edit()
            edit.putString("uuid", uuid)
            edit.apply()
        }
        return uuid ?: ""
    }
}