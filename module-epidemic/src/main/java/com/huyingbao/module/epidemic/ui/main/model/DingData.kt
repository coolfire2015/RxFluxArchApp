package com.huyingbao.module.epidemic.ui.main.model

/**
 * 丁香园返回数据
 */
data class DingData(
        /**
         * 功能条目
         */
        val getEntries: ArrayList<Entry>,
        /**
         * 建议知识
         */
        val getIndexRecommendList: ArrayList<IndexRecommend>,
        /**
         * 谣言
         */
        val getIndexRumorList: ArrayList<IndexRumor>,
        /**
         * 省数据
         */
        val getListByCountryTypeService1: ArrayList<AreaStat>,
        /**
         * 全球数据
         */
        val getListByCountryTypeService2: ArrayList<AreaStat>,
        /**
         * 省市数据
         */
        val getAreaStat: ArrayList<AreaProvinceStat>,
        /**
         * 商品指南
         */
        val fetchGoodsGuide: ArrayList<GoodsGuide>,
        /**
         * 统计数据
         */
        val getStatisticsService: StatisticsService,
        /**
         * 时间线
         */
        val getTimelineService: ArrayList<GetTimelineService>,
        /**
         * 百科知识
         */
        val getWikiList: WikiList
)