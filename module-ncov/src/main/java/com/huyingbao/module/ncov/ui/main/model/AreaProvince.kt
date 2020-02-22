package com.huyingbao.module.ncov.ui.main.model

data class AreaProvince(
        val areaCities: List<AreaCity>,
        val comment: String,
        val continentEnglishName: String,
        val continentName: String,
        val countryEnglishName: String,
        val countryName: String,
        val locationId: Int,
        val provinceEnglishName: String,
        val provinceName: String,
        val provinceShortName: String,
        val updateTime: Long
) : Count()