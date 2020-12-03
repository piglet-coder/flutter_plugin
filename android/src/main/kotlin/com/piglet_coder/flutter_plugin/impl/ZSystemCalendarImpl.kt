package com.piglet_coder.flutter_plugin.impl

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import com.piglet_coder.flutter_plugin.utils.CalendarUtil
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.*


/**
 * @author zdl
 * date 2020/12/2 16:02
 * email zdl328465042@163.com
 * description 系统日历添加日程
 */
class ZSystemCalendarImpl(private val ctx: Context) : MethodChannel.MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        //通过methodCall可以获取参数和方法名  执行对应的平台业务逻辑即可
        val title = call.argument<String>("title") ?: "标题"
        when (call.method) {
            "addSchedule" -> {
                val description = call.argument<String>("description") ?: ""
                val startTime = call.argument<Long>("startTime") ?: 0
                val endTime = call.argument<Long>("endTime") ?: 0
                val reminderTime = call.argument<Int>("reminderTime") ?: 0
                val value = CalendarUtil.with(ctx).addSchedule(title, description, startTime, endTime, reminderTime)
                result.success(value)
            }
            "delSchedule" -> {
                val value = CalendarUtil.with(ctx).delSchedule(title)
                result.success(value)
            }
            else -> {
                result.notImplemented()
            }
        }
    }
}