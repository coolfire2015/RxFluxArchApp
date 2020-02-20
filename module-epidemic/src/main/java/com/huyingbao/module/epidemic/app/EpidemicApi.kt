package com.huyingbao.module.epidemic.app

import com.huyingbao.module.epidemic.ui.main.model.AreaProvince
import com.huyingbao.module.epidemic.ui.main.model.DingResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 丁香园数据
 */
interface DingApi {
    companion object{
        const val DING_API = "http://lab.isaaclin.cn/"
    }

    @GET("nCoV/api/area")
    fun getArea(): Observable<DingResponse<ArrayList<AreaProvince>>>
}

/**
 * 新闻搜集数据
 */
interface NewsApi{
    companion object{
        const val NEWS_API="http://ncov.news.dragon-yuan.me/"
    }

}