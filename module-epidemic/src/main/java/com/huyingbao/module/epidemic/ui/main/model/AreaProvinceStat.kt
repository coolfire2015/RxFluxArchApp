package com.huyingbao.module.epidemic.ui.main.model

/**
 * 省数据
 */
data class AreaProvinceStat(
    val cities: List<AreaCityStat>,
    val comment: String,
    val confirmedCount: Int,
    val curedCount: Int,
    val deadCount: Int,
    val provinceName: String,
    val provinceShortName: String,
    val suspectedCount: Int
)