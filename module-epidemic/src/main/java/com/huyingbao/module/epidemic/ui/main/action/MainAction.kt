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