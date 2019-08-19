package com.huyingbao.module.gan.ui.random.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface RandomAction {
    companion object {
        const val TO_SHOW_DATA = "toShowData"

        /**
         * 获取文章数据列表
         */
        const val GET_DATA_LIST = "getDataList"
    }

    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     * @return
     */
    fun getDataList(category: String, count: Int, page: Int)
}
