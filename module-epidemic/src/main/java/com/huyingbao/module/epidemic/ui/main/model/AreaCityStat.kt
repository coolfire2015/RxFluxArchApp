package com.huyingbao.module.epidemic.ui.main.model

/**
 * 地级市数据
 */
data class AreaCityStat(
    val cityName: String,
    val comment: String,
    val confirmedCount: Int,
    val countryType: Int,
    val createTime: Long,
    val curedCount: Int,
    val deadCount: Int,
    val id: Int,
    val modifyTime: Long,
    val `operator`: String,
    val provinceId: String,
    val provinceName: String,
    val provinceShortName: String,
    val sort: Int,
    val suspectedCount: Int,
    val tags: String
)