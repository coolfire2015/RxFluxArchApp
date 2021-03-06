package com.huyingbao.module.common.utils

import com.huyingbao.core.utils.TimeUtils.getNewsTimeStr
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    /**
     * 格式化UTC时间（协调世界时）
     */
    fun formatUTCTime(utcTime: String): String {
        return try {
            //时间格式
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            //设置时区UTC
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            //格式化，转当地时区时间
            getNewsTimeStr(dateFormat.parse(utcTime))
//            //格式化，转当地时区时间
//            val date = dateFormat.parse(utcTime)
//            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss")
//            //默认时区
//            dateFormat.timeZone = TimeZone.getDefault()
//            dateFormat.format(date)
        } catch (e: ParseException) {
            ""
        }
    }
}
