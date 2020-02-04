package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class Data(
        val getAreaStat: List<AreaProvinceStat>,
        val getEntries: List<Entry>,
        val getIndexRecommendList: List<IndexRecommend>,
        val getIndexRumorList: List<IndexRumor>,
        val getListByCountryTypeService: List<AreaCityStat>,
        val getListByCountryTypeService2: List<AreaCityStat>,
        val getPV: Int,
        val getStatisticsService: StatisticsService,
        val getTimelineService: List<GetTimelineService>,
        val getWikiList: WikiList,
        val showPuppeteerUA: String
)