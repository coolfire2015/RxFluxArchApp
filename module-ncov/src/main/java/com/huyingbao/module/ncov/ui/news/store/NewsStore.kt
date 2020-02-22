package com.huyingbao.module.ncov.ui.news.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.ncov.ui.news.action.NewsAction
import com.huyingbao.module.ncov.ui.news.model.NewsResponse
import com.huyingbao.module.ncov.ui.news.model.NewsResult
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    val newsLiveData by lazy {
        MutableLiveData<NewsResult>()
    }

    @Subscribe(tags = [NewsAction.GET_NEWS])
    fun onGetNews(rxAction: RxAction) {
        newsLiveData.value = rxAction.getResponse<NewsResponse>()?.result
    }
}

