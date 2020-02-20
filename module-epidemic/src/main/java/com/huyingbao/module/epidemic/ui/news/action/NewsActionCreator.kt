package com.huyingbao.module.epidemic.ui.news.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.epidemic.app.NewsApi
import javax.inject.Inject

@ActivityScope
class NewsActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val newsApi: NewsApi
) : RxActionCreator(rxDispatcher, rxActionManager), NewsAction {
    override fun getNews(page: Int) {
        val rxAction = newRxAction(NewsAction.GET_NEWS,
                CommonAppConstants.Key.PAGE, page)
        postHttpLoadingAction(rxAction, newsApi.getNews())
    }
}
