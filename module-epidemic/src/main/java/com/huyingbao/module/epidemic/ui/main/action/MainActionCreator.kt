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
    override fun getDingData() {
        val rxAction = newRxAction(MainAction.GET_DING_DATA).apply { isGlobalCatch = false }
        postHttpLoadingAction(rxAction, dingApi.getArea())
    }
}
