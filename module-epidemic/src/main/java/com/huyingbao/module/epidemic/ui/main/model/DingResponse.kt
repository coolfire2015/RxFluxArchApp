package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class DingResponse(
    val `data`: Data,
    val errorCode: Int,
    val errorMessage: String,
    val ok: Boolean
)