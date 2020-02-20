package com.huyingbao.module.epidemic.ui.main.action

interface MainAction {
    companion object {
        /**
         * 获取疫情概览
         */
        const val GET_OVER_ALL = "getOverAll"
        /**
         * 获取地域列表
         */
        const val GET_PROVINCE_NAME = "getProvinceName"
        /**
         * 获取地域数据
         */
        const val GET_AREA_DATA = "getAreaData"
        /**
         * 获取谣言
         */
        const val GET_RUMORS = "getRumors"
    }

    /**
     * 获取疫情概览
     */
    fun getOverAll()

    /**
     * 获取地域列表
     */
    fun getProvinceName()

    /**
     * 获取地域数据
     */
    fun getAreaData(province: String?)

    /**
     * 获取谣言
     */
    fun getRumors()
}