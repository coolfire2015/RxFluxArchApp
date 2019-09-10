package com.huyingbao.module.gan.ui.random.action

import com.huyingbao.module.gan.ui.random.model.Article
import com.huyingbao.module.gan.ui.random.model.GanResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface RandomAction {
    companion object {
        /**
         * 跳转对应类别的列表数据页面
         */
        const val TO_SHOW_DATA = "toShowData"

        /**
         * 获取文章数据列表
         */
        const val GET_DATA_LIST = "getDataList"
    }

    /**
     * 跳转对应类别的列表数据页面
     *
     * @param category 类别
     */
    fun toShowData(category: String)

    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     */
    fun getDataList(category: String, count: Int, page: Int)
}

interface RandomApi {
    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     */
    @GET("data/{category}/{count}/{page} ")
    fun getDataList(
            @Path("category") category: String,
            @Path("count") count: Int,
            @Path("page") page: Int): Observable<GanResponse<ArrayList<Article>>>
}
