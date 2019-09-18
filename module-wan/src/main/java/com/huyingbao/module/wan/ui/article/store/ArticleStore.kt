package com.huyingbao.module.wan.ui.article.store

import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.ioThread
import com.huyingbao.module.wan.app.WanAppDatabase
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import com.huyingbao.module.wan.ui.article.model.WanResponse
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleStore @Inject constructor(
        rxDispatcher: RxDispatcher,
        private var wanAppDatabase: WanAppDatabase
) : RxActivityStore(rxDispatcher) {
    /**
     * 默认起始页码
     */
    companion object {
        const val DEFAULT_PAGE = 0
    }

    /**
     * 列表页数
     */
    val pageLiveData by lazy {
        MutableLiveData<Int>().apply {
            value = DEFAULT_PAGE
        }
    }
    /**
     * 横幅数据
     */
    val bannerLiveData by lazy {
        MutableLiveData<ArrayList<Banner>>()
    }
    /**
     * 文章数据
     */
    val articleLiveData by lazy {
        wanAppDatabase.reposDao().getArticleList().toLiveData(
                config = Config(
                        //每次加载多少数据
                        pageSize = CommonAppConstants.Config.PAGE_SIZE,
                        //距底部还有几条数据时，加载下一页数据
                        prefetchDistance = 10,
                        //第一次加载多少数据
                        initialLoadSizeHint = 60,
                        //是否启用占位符，若为true，则视为固定数量的item
                        enablePlaceholders = true),
                boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
                    override fun onItemAtEndLoaded(itemAtEnd: Article) {
                        super.onItemAtEndLoaded(itemAtEnd)
                        //Page滑动到底部，通知UI需要获取下一页数据
                        postChange(RxChange.newInstance(CommonAppAction.GET_NEXT_PAGE))
                    }
                })
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        pageLiveData.value = DEFAULT_PAGE
        bannerLiveData.value = null
    }

    /**
     * 接收BannerList数据
     */
    @Subscribe(tags = [ArticleAction.GET_BANNER_LIST])
    fun onGetBannerLiveData(rxAction: RxAction) {
        val bannerResponse = rxAction.getResponse<WanResponse<ArrayList<Banner>>>()
        bannerLiveData.value = bannerResponse!!.data
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     *
     * 使用[ioThread]方法，数据库操作在IO线程运行，
     * 不对外调用[RxActivityStore.postChange]方法。
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST])
    fun onGetArticleList(rxAction: RxAction) {
        val page = rxAction.get<Int>(CommonAppConstants.Key.PAGE) ?: DEFAULT_PAGE
        //IO线程操作
        ioThread {
            //如果是刷新，先清除数据库缓存
            if (page == DEFAULT_PAGE) {
                wanAppDatabase.reposDao().deleteAll()
            }
            //如果有数据，添加到数据库缓存中
            rxAction.getResponse<WanResponse<Page<Article>>>()?.data?.datas?.let {
                wanAppDatabase.reposDao().insertAll(it)
            }
        }
        //主线程，更新分页索引
        pageLiveData.value = page + 1
    }
}
