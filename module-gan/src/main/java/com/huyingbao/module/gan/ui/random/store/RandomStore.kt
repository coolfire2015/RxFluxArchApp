package com.huyingbao.module.gan.ui.random.store

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.gan.app.GanAppDatabase
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.model.Article
import com.huyingbao.module.gan.ui.random.model.GanResponse
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 如果OS销毁app释放资源，用户数据不会丢失；
 * 当网络很差或者断网的时候app可以继续工作。
 *
 * ViewModel的目的是获取并保存Activity或Fragment所必需的信息
 * Activity或Fragment应该能够观察到ViewModel中的变化
 *
 * ViewModel对象在获取ViewModel时被限定为传递给ViewModelProvider的生命周期。
 * ViewModel保留在内存中，直到Activity销毁或Fragment分离之前。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RandomStore @Inject constructor(
        rxDispatcher: RxDispatcher,
        private var ganAppDatabase: GanAppDatabase
) : RxActivityStore(rxDispatcher) {
    /**
     * 默认起始页码
     */
    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_CATEGORY = "Android"
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     *
     * 使用[doAsync]方法，数据库操作在IO线程运行，
     * 主线程对外调用[RxActivityStore.postChange]方法。
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST])
    fun onGetDataList(rxAction: RxAction) {
        //异步线程操作
        doAsync {
            //数据库事务操作
            ganAppDatabase.runInTransaction {
                //如果是刷新，先清除数据库缓存
                if (rxAction[CommonAppConstants.Key.PAGE] ?: DEFAULT_PAGE == DEFAULT_PAGE) {
                    ganAppDatabase.reposDao().deleteAll(
                            rxAction[CommonAppConstants.Key.CATEGORY] ?: DEFAULT_CATEGORY
                    )
                }
                //如果有数据，添加到数据库缓存中
                rxAction.getResponse<GanResponse<ArrayList<Article>>>()?.results?.let {
                    ganAppDatabase.reposDao().insertAll(it)
                }
            }
        }
        //主线程，通知获取网络数据成功
        postChange(RxChange.newInstance(rxAction.tag))
    }

    /**
     * 文章数据，当[category]数据更新时，从数据库更新对应类别的数据，
     * 每个Fragment实例化一个绑定数据库的LiveData。
     */
    fun getArticleLiveData(category: String): LiveData<PagedList<Article>> =
            ganAppDatabase.reposDao().getArticleList(category).toLiveData(
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
                    boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
                        override fun onItemAtEndLoaded(itemAtEnd: Article) {
                            super.onItemAtEndLoaded(itemAtEnd)
                            //Page滑动到底部，通知UI需要获取下一页数据
                            postChange(RxChange.newInstance(CommonAppAction.GET_NEXT_PAGE))
                        }
                    }
            )
}
