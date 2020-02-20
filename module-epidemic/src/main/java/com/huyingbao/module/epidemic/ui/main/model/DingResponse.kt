package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class DingResponse<T>(
        var results: T? = null,
        val success: Boolean
)