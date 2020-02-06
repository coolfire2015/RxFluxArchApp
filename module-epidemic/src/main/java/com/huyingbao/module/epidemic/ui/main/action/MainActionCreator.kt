package com.huyingbao.module.epidemic.ui.main.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.epidemic.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), MainAction {
    override fun getDingData() {
        val rxAction=newRxAction(MainAction.GET_DING_DATA).apply { isGlobalCatch=false }
        postHttpLoadingAction(rxAction,retrofit.create(MainApi::class.java).getDingData())
    }
}
