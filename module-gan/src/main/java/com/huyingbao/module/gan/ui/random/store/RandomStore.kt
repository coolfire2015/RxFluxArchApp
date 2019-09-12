package com.huyingbao.module.gan.ui.random.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.tools.ioThread
import com.huyingbao.module.gan.app.GanAppDatabase
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.model.Article
import com.huyingbao.module.gan.ui.random.model.GanResponse
import org.greenrobot.eventbus.Subscribe
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
     * 存储当前文章类别和页码
     */
    val configLiveData by lazy { MutableLiveData<Pair<String, Int>>() }
    /**
     * 文章数据，当[configLiveData]数据更新时，从数据库更新对应类别的数据
     */
    val articleLiveData by lazy {
        Transformations.switchMap(configLiveData) {
            it?.let {
                ganAppDatabase.reposDao().getArticleList(it.first).toLiveData(
                        config = Config(pageSize = 30, enablePlaceholders = true),
                        boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
                            override fun onItemAtEndLoaded(itemAtEnd: Article) {
                                super.onItemAtEndLoaded(itemAtEnd)
                                //Page滑动到底部，通知UI需要获取下一页数据
                                postChange(RxChange.newInstance(RandomAction.GET_NEXT_PAGE))
                            }
                        }
                )
            }
        }
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        configLiveData.value = null
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST])
    fun onGetDataList(rxAction: RxAction) {
        //存储当前的页码
        val page = rxAction[CommonAppConstants.Key.PAGE] ?: DEFAULT_PAGE
        //存储当前的类别
        val category = rxAction[CommonAppConstants.Key.CATEGORY] ?: DEFAULT_CATEGORY
        //更新当前存储配置数据
        configLiveData.value = Pair(category, page + 1)
        //在IO线程中更新数据库
        ioThread {
            //数据库事务操作
            ganAppDatabase.runInTransaction {
                //如果是刷新，先清除数据库缓存
                if (page == DEFAULT_PAGE) {
                    ganAppDatabase.reposDao().deleteAll(category)
                }
                //如果有数据，添加到数据库缓存中
                rxAction.getResponse<GanResponse<ArrayList<Article>>>()?.results?.let {
                    ganAppDatabase.reposDao().insertAll(it)
                }
            }
        }
        //通知UI，获取数据成功，需要更新页面索引
        postChange(RxChange.newInstance(rxAction.tag))
    }
}
