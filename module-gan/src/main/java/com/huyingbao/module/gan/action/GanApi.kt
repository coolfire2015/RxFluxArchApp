package com.huyingbao.module.gan.action

import com.huyingbao.module.gan.ui.random.model.Product

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface GanApi {
    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     * @return
     */
    @GET("data/{category}/{count}/{page} ")
    fun getDataList(
            @Path("category") category: String,
            @Path("count") count: Int,
            @Path("page") page: Int): Observable<GanResponse<Product>>
}
