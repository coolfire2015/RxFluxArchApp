package com.huyingbao.module.epidemic.ui.main.model

/**
 * 百科知识列表
 */
data class WikiList(
    val pageNum: Int,
    val pageSize: Int,
    val result: List<Result>,
    val total: Int
)