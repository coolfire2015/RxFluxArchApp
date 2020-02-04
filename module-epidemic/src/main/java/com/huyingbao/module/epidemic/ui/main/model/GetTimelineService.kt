package com.huyingbao.module.epidemic.ui.main.model

data class GetTimelineService(
    val createTime: Long,
    val id: Int,
    val infoSource: String,
    val modifyTime: Long,
    val provinceId: String,
    val provinceName: String,
    val pubDate: Long,
    val pubDateStr: String,
    val sourceUrl: String,
    val summary: String,
    val title: String
)