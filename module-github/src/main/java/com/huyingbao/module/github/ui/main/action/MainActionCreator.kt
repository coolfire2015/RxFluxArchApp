package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.utils.FlatMapResponse2Result
import com.huyingbao.core.utils.FlatMapResult2Response
import com.huyingbao.module.common.ui.update.action.AppAction
import com.huyingbao.module.common.ui.update.action.FirApi
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubActionCreator
import com.huyingbao.module.github.ui.issue.model.Issue
import com.huyingbao.module.github.ui.main.model.Repos
import com.huyingbao.module.github.ui.main.model.ReposConversion
import com.huyingbao.module.github.ui.main.model.TrendConversion
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), MainAction, AppAction {
    @Inject
    lateinit var firApi: FirApi

    override fun getAppLatest(id: String, token: String) {
        val rxAction = newRxAction(AppAction.GET_APP_LATEST)
        postHttpLoadingAction(rxAction, firApi.getAppLatest(id, token))
    }

    override fun feedback(editContent: String) {
        val rxAction = newRxAction(MainAction.FEED_BACK)
        val issue = Issue()
        issue.title = "用户反馈"
        issue.body = editContent
        postHttpLoadingAction(rxAction, retrofit.create(MainApi::class.java).createIssue(
                "coolfire2015",
                "RxFluxArchitecture",
                issue
        ))
    }

    override fun getNewsEvent(user: String, page: Int) {
        val rxAction = newRxAction(MainAction.GET_NEWS_EVENT)
        postHttpAction(rxAction, retrofit.create(MainApi::class.java).getNewsEvent(user, page))
    }

    override fun getTrendData(languageType: String, since: String) {
        val rxAction = newRxAction(MainAction.GET_TREND_DATA)
        val httpObservable = retrofit.create(MainApi::class.java)
                .getTrendData(true, languageType, since)
                //response转String
                .flatMap { FlatMapResponse2Result(it) }
                //String转ArrayList<Trend>转ArrayList<ReposUIModel>
                .map {
                    //String转ArrayList<Trend>
                    val trendList = TrendConversion.htmlToRepo(it)
                    //ArrayList<Trend>转ArrayList<ReposUIModel>
                    val reposList = ArrayList<Repos>()
                    for (reposUi in trendList) {
                        reposList.add(ReposConversion.trendToReposUIModel(reposUi))
                    }
                    reposList
                }
                //List转Response
                .flatMap { FlatMapResult2Response(it) }
        postHttpAction(rxAction, httpObservable)
    }
}