package com.huyingbao.module.gan.action

import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
class GanResponse<T> {
    var isError: Boolean = false
    var results: ArrayList<T>? = null
}
