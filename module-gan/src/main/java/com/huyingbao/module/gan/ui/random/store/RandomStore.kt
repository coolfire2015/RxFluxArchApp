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
import com.huyingbao.module.gan.app.GanAppDatabase
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.model.Article
import com.huyingbao.module.gan.ui.random.model.GanResponse
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
        private var ganAppDatabase: GanAppDatabase,
        private var randomActionCreator: RandomActionCreator
) : RxActivityStore(rxDispatcher) {
    /**
     * 默认起始页码
     */
    companion object {
        const val DEFAULT_PAGE = 1
    }

    /**
     * 列表页数
     */
    var nextRequestPage = DEFAULT_PAGE
        private set
    /**
     * 文章类别
     */
    val categoryLiveData by lazy { MutableLiveData<String>() }
    /**
     * 文章数据
     */
    val articleLiveData by lazy {
        Transformations.switchMap(categoryLiveData) {
            ganAppDatabase.reposDao().getArticleList(it).toLiveData(
                    config = Config(pageSize = 30, enablePlaceholders = true),
                    boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
                        override fun onItemAtEndLoaded(itemAtEnd: Article) {
                            super.onItemAtEndLoaded(itemAtEnd)
                            randomActionCreator.getDataList(it, CommonAppConstants.Config.PAGE_SIZE, nextRequestPage)
                        }
                    }
            )
        }
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        nextRequestPage = DEFAULT_PAGE
        categoryLiveData.value = null
    }

    /**
     * 跳转数据列表显示页面
     */
    @Subscribe(tags = [RandomAction.TO_SHOW_DATA])
    fun toShowData(rxAction: RxAction) {
        onCleared()//跳转页面，先清除旧数据
        categoryLiveData.value = rxAction.get<String>(CommonAppConstants.Key.CATEGORY)
        postChange(RxChange.newInstance(rxAction.tag))
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     */
    @Subscribe(tags = [RandomAction.GET_DATA_LIST], threadMode = ThreadMode.BACKGROUND)
    fun setProductListLiveData(rxAction: RxAction) {
        //如果是刷新，先清除数据库缓存
        nextRequestPage = rxAction.get<Int>(CommonAppConstants.Key.PAGE) ?: DEFAULT_PAGE
        if (nextRequestPage == DEFAULT_PAGE) {
            categoryLiveData.value?.let { ganAppDatabase.reposDao().deleteAll(it) }
        }
        //如果有数据，添加到数据库缓存中
        rxAction.getResponse<GanResponse<ArrayList<Article>>>()?.results?.let {
            ganAppDatabase.reposDao().insertAll(it)
        }
        //更新分页索引
        nextRequestPage++
    }
}
