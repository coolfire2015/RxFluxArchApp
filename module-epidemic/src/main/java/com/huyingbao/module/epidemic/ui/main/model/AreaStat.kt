package com.huyingbao.module.epidemic.ui.main.model

/**
 * 地区数据
 */
data class AreaStat(
        val id: Int,
        /**
         * 1:省，2:国家
         */
        val countryType: Int,
        val cityName: String,
        val provinceId: String,
        val provinceName: String,
        val provinceShortName: String,
        val createTime: Long,
        val modifyTime: Long,
        val sort: Int,
        val comment: String,
        val tags: String
) : AreaBase()