package com.piglet_coder.flutter_plugin.utils

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import java.util.*


/**
 * @author zdl
 * date 2020/12/3 10:17
 * email zdl328465042@163.com
 * description 系统日历工具类
 */
class CalendarUtil {

    private val calendarUri = Uri.parse("content://com.android.calendar/calendars")
    private val calendarEventUri = Uri.parse("content://com.android.calendar/events")
    private val calendarReminderUri = Uri.parse("content://com.android.calendar/reminders")

    private val calendarName = "boohee"
    private val calendarAccountName = "BOOHEE@boohee.com"
    private val calendarAccountType = "com.android.boohee"
    private val calendarDisplayName = "BOOHEE账户"

    companion object {
        private lateinit var ctx: Context
        private lateinit var util: CalendarUtil

        fun with(ctx: Context): CalendarUtil {
            this.ctx = ctx
            if (!::util.isInitialized) util = CalendarUtil()
            return util
        }
    }

    /**
     * 获取用户id
     */
    private fun checkAndAddCalendarAccount(): Int {
        val oldId = checkCalendarAccount()
        return if (oldId >= 0) {
            oldId
        } else {
            val addId: Long = addCalendarAccount()
            if (addId >= 0) {
                checkCalendarAccount()
            } else {
                -1
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    private fun checkCalendarAccount(): Int {
        val userCursor = ctx.contentResolver.query(calendarUri, null, null, null, null)
        return userCursor.use { userCursor ->
            if (userCursor == null) { //查询返回空值
                return -1
            }
            val count: Int = userCursor.count
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst()
                userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
            } else {
                -1
            }
        }
    }

    /**
     * 添加用户id
     */
    private fun addCalendarAccount(): Long {
        val value = ContentValues()
        value.put(CalendarContract.Calendars.NAME, calendarName)
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, calendarAccountName)
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, calendarAccountType)
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, calendarDisplayName)
        value.put(CalendarContract.Calendars.VISIBLE, 1)
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER)
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().id)
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, calendarAccountName)
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri = calendarUri
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, calendarAccountName)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, calendarAccountType)
                .build()
        val result = ctx.contentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 添加日程，是否提醒可选
     * @param reminderTime 提前几分钟提醒
     */
    fun addSchedule(title: String, description: String, dtStart: Long, dtEnd: Long, reminderTime: Int): Boolean {
        val event = ContentValues()
        event.put(CalendarContract.Events.CALENDAR_ID, checkAndAddCalendarAccount())
        event.put(CalendarContract.Events.TITLE, title)
        event.put(CalendarContract.Events.DESCRIPTION, description)
        event.put(CalendarContract.Events.DTSTART, dtStart)
        event.put(CalendarContract.Events.DTEND, dtEnd)
//        event.put(CalendarContract.Events.HAS_ALARM, 1)
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT+8:00")
        //添加日程失败直接返回
        val newPlan = ctx.contentResolver.insert(calendarEventUri, event) ?: return false
        if (reminderTime == 0) {
            return true
        }
        //事件提醒的设定
        val values = ContentValues()
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newPlan))
        // 提前previousDate天有提醒
        values.put(CalendarContract.Reminders.MINUTES, reminderTime)
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        //添加事件提醒失败直接返回
        val reminder = ctx.contentResolver.insert(calendarReminderUri, values)
        return reminder != null
    }

    /**
     * 删除日程
     */
    fun delSchedule(title: String): Boolean {
        val eventCursor = ctx.contentResolver.query(calendarEventUri,
                null, null, null, null)
        eventCursor.use { eventCursor ->
            if (eventCursor == null) //查询返回空值
                return false
            if (eventCursor.count > 0) {
                var isDel = false
                //遍历所有事件，找到title跟需要查询的title一样的项
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"))
                    if (!TextUtils.isEmpty(title) && title == eventTitle) {
                        val id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID)) //取得id
                        val deleteUri = ContentUris.withAppendedId(calendarEventUri, id.toLong())
                        val rows: Int = ctx.contentResolver.delete(deleteUri, null, null)
                        isDel = rows != -1
                    }
                    eventCursor.moveToNext()
                }
                return isDel
            }else{
                return false
            }
        }
    }
}