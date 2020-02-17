package com.huyingbao.module.epidemic.ui.main.model

/**
 * 防疫建议
 */
data class IndexRecommend(
        val contentType: Int,
        val createTime: Long,
        val id: Int,
        val imgUrl: String,
        val linkUrl: String,
        val modifyTime: Long,
        val `operator`: String,
        val recordStatus: Int,
        val sort: Int,
        val title: String
)