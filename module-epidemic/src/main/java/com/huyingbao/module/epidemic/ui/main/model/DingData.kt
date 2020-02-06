package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class DingData(
        val getAreaStat: ArrayList<AreaProvinceStat>,
        val getEntries: ArrayList<Entry>,
        val getIndexRecommendList: ArrayList<IndexRecommend>,
        val getIndexRumorList: ArrayList<IndexRumor>,
        /**
         * 国家数据
         */
        val getListByCountryTypeService: ArrayList<AreaCityStat>,
        /**
         * 省市数据
         */
        val getListByCountryTypeService2: ArrayList<AreaCityStat>,
        val getPV: Int,
        val getStatisticsService: StatisticsService,
        val getTimelineService: ArrayList<GetTimelineService>,
        val getWikiList: WikiList,
        val showPuppeteerUA: String
)