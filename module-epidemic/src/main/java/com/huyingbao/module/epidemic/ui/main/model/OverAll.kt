package com.huyingbao.module.epidemic.ui.main.model

/**
 * 病毒研究情况以及全国疫情概览
 */
data class OverAll(
        val abroadRemark: String,
        val confirmedIncr: Int,
        val curedIncr: Int,
        val currentConfirmedIncr: Int,
        val deadIncr: Int,
        val generalRemark: String,
        val note1: String,
        val note2: String,
        val note3: String,
        val remark1: String,
        val remark2: String,
        val remark3: String,
        val remark4: String,
        val remark5: String,
        val seriousCount: Int,
        val seriousIncr: Int,
        val suspectedIncr: Int,
        val updateTime: Long
) : Count()