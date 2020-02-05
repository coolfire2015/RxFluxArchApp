package com.huyingbao.module.epidemic.ui.main.model

/**
 * 统计数据
 */
data class StatisticsService(
    val abroadRemark: String,
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val countRemark: String,
    val createTime: Long,
    val curedCount: Int,
    val curedIncr: Int,
    val dailyPic: String,
    val dailyPics: List<String>,
    val deadCount: Int,
    val deadIncr: Int,
    val deleted: Boolean,
    val generalRemark: String,
    val id: Int,
    val imgUrl: String,
    val infectSource: String,
    val marquee: List<Any>,
    val modifyTime: Long,
    val note1: String,
    val note2: String,
    val note3: String,
    val passWay: String,
    val remark1: String,
    val remark2: String,
    val remark3: String,
    val remark4: String,
    val remark5: String,
    val seriousCount: Int,
    val seriousIncr: Int,
    val summary: String,
    val suspectedCount: Int,
    val suspectedIncr: Int,
    val virus: String
)