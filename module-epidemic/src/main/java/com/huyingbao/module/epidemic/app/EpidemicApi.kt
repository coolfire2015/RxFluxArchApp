package com.huyingbao.module.epidemic.app

import com.huyingbao.module.epidemic.ui.main.model.AreaProvince
import com.huyingbao.module.epidemic.ui.main.model.DingResponse
import com.huyingbao.module.epidemic.ui.main.model.OverAll
import com.huyingbao.module.epidemic.ui.main.model.Rumor
import com.huyingbao.module.epidemic.ui.news.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * 丁香园数据
 */
interface DingApi {
    companion object {
        const val DING_API = "http://lab.isaaclin.cn/"
    }

    @GET("nCoV/api/overall")
    fun getOverAll(): Observable<DingResponse<ArrayList<OverAll>>>

    @GET("nCoV/api/area")
    fun getAreaData(): Observable<DingResponse<ArrayList<AreaProvince>>>

    @GET("nCoV/api/rumors")
    fun getRumors(): Observable<DingResponse<ArrayList<Rumor>>>
}

/**
 * 新闻搜集数据
 */
interface NewsApi {
    companion object {
        const val NEWS_API = "http://ncov.news.dragon-yuan.me/"
    }

    @GET("api/news")
    fun getNews(): Observable<NewsResponse>
}