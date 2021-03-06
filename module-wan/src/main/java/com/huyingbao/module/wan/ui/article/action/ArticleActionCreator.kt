package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanActionCreator
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * rxAction创建发送管理类
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : WanActionCreator(rxDispatcher, rxActionManager), ArticleAction {

    override fun getArticleList(page: Int) {
        val rxAction = newRxAction(ArticleAction.GET_ARTICLE_LIST,
                CommonAppConstants.Key.PAGE, page)
        rxAction.isGlobalCatch = false
        val articleApi = retrofit.create(ArticleApi::class.java)
        postHttpLoadingAction(rxAction, articleApi.getArticleList(page))
    }

    override fun getBannerList() {
        val rxAction = newRxAction(ArticleAction.GET_BANNER_LIST)
        rxAction.isGlobalCatch = false
        //接口调用失败，自动重复调用5次，每次间隔3s
        val httpObservable = retrofit.create(ArticleApi::class.java)
                .getBannerList()
                .retryWhen(retryAction(5, 3))
                .flatMap(verifyResponse())
        postHttpLoadingAction(rxAction, httpObservable)
    }
}
