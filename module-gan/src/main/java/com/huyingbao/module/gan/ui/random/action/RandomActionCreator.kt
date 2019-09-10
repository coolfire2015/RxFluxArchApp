package com.huyingbao.module.gan.ui.random.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.gan.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RandomActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), RandomAction {
    override fun toShowData(category: String) {
        val rxAction = newRxAction(RandomAction.TO_SHOW_DATA,
                CommonAppConstants.Key.CATEGORY, category)
        postRxAction(rxAction)
    }

    override fun getDataList(category: String, count: Int, page: Int) {
        val rxAction = newRxAction(RandomAction.GET_DATA_LIST,
                CommonAppConstants.Key.COUNT, count,
                CommonAppConstants.Key.PAGE, page)
        rxAction.isGlobalCatch = false
        postHttpLoadingAction(rxAction, retrofit.create(RandomApi::class.java).getDataList(category, count, page))
    }
}
