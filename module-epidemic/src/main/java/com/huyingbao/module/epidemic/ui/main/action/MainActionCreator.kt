package com.huyingbao.module.epidemic.ui.main.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.epidemic.app.DingApi
import javax.inject.Inject

@ActivityScope
class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val dingApi: DingApi
) : RxActionCreator(rxDispatcher, rxActionManager), MainAction {
    override fun getOverAll() {
        val rxAction = newRxAction(MainAction.GET_OVER_ALL).apply { isGlobalCatch = false }
        postHttpLoadingAction(rxAction, dingApi.getOverAll())
    }

    override fun getProvinceName() {
        val rxAction = newRxAction(MainAction.GET_PROVINCE_NAME).apply { isGlobalCatch = false }
        postHttpLoadingAction(rxAction, dingApi.getProvinceName())
    }

    override fun getAreaData(province: String?) {
        val rxAction = newRxAction(MainAction.GET_AREA_DATA).apply { isGlobalCatch = false }
        postHttpLoadingAction(rxAction, dingApi.getAreaData())
    }

    override fun getRumors() {
        val rxAction = newRxAction(MainAction.GET_AREA_DATA).apply { isGlobalCatch = false }
        postHttpLoadingAction(rxAction, dingApi.getRumors())
    }
}
