package com.huyingbao.module.epidemic.ui.main.action

import com.huyingbao.module.epidemic.ui.main.model.DingResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface MainAction {
    companion object {
        /**
         * 获取丁香园数据
         */
        const val GET_DING_DATA = "getDingData"

        const val TO_PROVINCE = "to_province"
        const val TO_CITY = "bt_city"
        const val TO_COUNTRY = "bt_country"
        const val TO_ENTRIES = "bt_entries"
        const val TO_RECOMMEND = "bt_recommend"
        const val TO_RUMOR = "bt_rumor"
        const val TO_GOODS = "bt_goods"
        const val TO_STATISTICS = "bt_statistics"
        const val TO_TIMELINE = "bt_timeline"
        const val TO_WIKI = "to_wiki"
    }

    /**
     * 获取丁香园数据
     */
    fun getDingData()
}

interface MainApi {
    /**
     * 获取丁香园数据
     */
    @GET("release/dingxiangyuan")
    fun getDingData(): Observable<DingResponse>
}