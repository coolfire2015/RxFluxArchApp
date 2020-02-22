package com.huyingbao.module.ncov.ui.main.model

/**
 * 丁香园返回数据
 */
data class DingResponse<T>(
        var results: T? = null,
        val success: Boolean
)