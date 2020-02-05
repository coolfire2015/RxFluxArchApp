package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class Data(
        val getAreaStat: List<AreaProvinceStat>,
        val getEntries: List<Entry>,
        val getIndexRecommendList: List<IndexRecommend>,
        val getIndexRumorList: List<IndexRumor>,
        /**
         * 国家数据
         */
        val getListByCountryTypeService: List<AreaCityStat>,
        /**
         * 省市数据
         */
        val getListByCountryTypeService2: List<AreaCityStat>,
        val getPV: Int,
        val getStatisticsService: StatisticsService,
        val getTimelineService: List<GetTimelineService>,
        val getWikiList: WikiList,
        val showPuppeteerUA: String
)