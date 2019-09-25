package com.huyingbao.module.github.ui.main.store

import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.github.app.GithubAppDatabase
import com.huyingbao.module.github.ui.main.action.MainAction
import com.huyingbao.module.github.ui.main.model.Event
import com.huyingbao.module.github.ui.main.model.Repos
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class MainStore @Inject constructor(
        rxDispatcher: RxDispatcher,
        private var githubAppDatabase: GithubAppDatabase
) : RxActivityStore(rxDispatcher) {
    /**
     * 默认起始页码
     */
    companion object {
        const val DEFAULT_PAGE = 1
    }

    /**
     * 动态事件数据
     */
    val eventListLiveData by lazy {
        githubAppDatabase.eventDao().getAll().toLiveData(
                config = Config(
                        //每次加载多少数据
                        pageSize = CommonAppConstants.Config.PAGE_SIZE,
                        //距底部还有几条数据时，加载下一页数据
                        prefetchDistance = 10,
                        //第一次加载多少数据
                        initialLoadSizeHint = 60,
                        //是否启用占位符，若为true，则视为固定数量的item
                        enablePlaceholders = true
                ),
                boundaryCallback = object : PagedList.BoundaryCallback<Event>() {
                    override fun onItemAtEndLoaded(itemAtEnd: Event) {
                        super.onItemAtEndLoaded(itemAtEnd)
                        //Page滑动到底部，通知UI需要获取下一页数据
                        postChange(RxChange.newInstance(CommonAppAction.GET_NEXT_PAGE))
                    }
                }
        )
    }
    /**
     * 推荐趋势仓库数据
     */
    val trendListLiveData by lazy {
        githubAppDatabase.reposDao().getAll().toLiveData(
                config = Config(
                        //每次加载多少数据
                        pageSize = CommonAppConstants.Config.PAGE_SIZE,
                        //距底部还有几条数据时，加载下一页数据
                        prefetchDistance = 10,
                        //第一次加载多少数据
                        initialLoadSizeHint = 60,
                        //是否启用占位符，若为true，则视为固定数量的item
                        enablePlaceholders = true
                ),
                boundaryCallback = object : PagedList.BoundaryCallback<Repos>() {
                    override fun onItemAtEndLoaded(itemAtEnd: Repos) {
                        super.onItemAtEndLoaded(itemAtEnd)
                        //Page滑动到底部，通知UI需要获取下一页数据
                        postChange(RxChange.newInstance(CommonAppAction.GET_NEXT_PAGE))
                    }
                }
        )
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     *
     * 使用[doAsync]方法，数据库操作在IO线程运行，
     * 主线程对外调用[RxActivityStore.postChange]方法。
     */
    @Subscribe(tags = [MainAction.GET_NEWS_EVENT])
    fun onGetNewsEvent(rxAction: RxAction) {
        //异步线程操作
        doAsync {
            //数据库事务操作
            githubAppDatabase.runInTransaction {
                //如果是刷新，先清除数据库缓存
                if (rxAction[CommonAppConstants.Key.PAGE] ?: DEFAULT_PAGE == DEFAULT_PAGE) {
                    githubAppDatabase.eventDao().deleteAll()
                }
                //如果有数据，添加到数据库缓存中
                rxAction.getResponse<ArrayList<Event>>()?.let {
                    githubAppDatabase.eventDao().insertAll(it)
                }
            }
        }
        //主线程，通知获取网络数据成功
        postChange(RxChange.newInstance(rxAction.tag))
    }

    @Subscribe(tags = [MainAction.GET_TREND_DATA], threadMode = ThreadMode.BACKGROUND)
    fun onGetTrend(rxAction: RxAction) {
        rxAction.getResponse<List<Repos>>()?.let {
            githubAppDatabase.reposDao().insertAll(it)
        }
    }
}