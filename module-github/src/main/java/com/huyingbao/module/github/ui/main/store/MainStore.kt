package com.huyingbao.module.github.ui.main.store

import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.github.app.GithubAppDatabase
import com.huyingbao.module.github.ui.main.action.MainAction
import com.huyingbao.module.github.ui.main.model.Event
import com.huyingbao.module.github.ui.main.model.Repos
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
     * 动态事件数据
     */
    val eventListLiveData by lazy {
        MutableLiveData<ArrayList<Event>>()
    }
    /**
     * 推荐趋势仓库数据
     */
    val trendListLiveData by lazy {
        githubAppDatabase.reposDao().getReposList().toLiveData(
                config = Config(
                        //每次加载多少数据
                        pageSize = 30,
                        //是否启用占位符，若为true，则视为固定数量的item
                        enablePlaceholders = true)
        )
    }

    override fun onCleared() {
        super.onCleared()
        eventListLiveData.value = null
    }

    @Subscribe(tags = [MainAction.GET_NEWS_EVENT])
    fun onGetNewsEvent(rxAction: RxAction) {
        eventListLiveData.value = rxAction.getResponse()
    }

    @Subscribe(tags = [MainAction.GET_TREND_DATA], threadMode = ThreadMode.BACKGROUND)
    fun onGetTrend(rxAction: RxAction) {
        rxAction.getResponse<List<Repos>>()?.let {
            githubAppDatabase.reposDao().insertAll(it)
        }
    }
}