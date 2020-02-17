package com.huyingbao.module.epidemic.ui.main.model

/**
 * 省数据
 */
data class AreaProvinceStat(
        val cities: List<AreaStat>,
        val comment: String,
        val provinceName: String,
        val provinceShortName: String
) : AreaBase()