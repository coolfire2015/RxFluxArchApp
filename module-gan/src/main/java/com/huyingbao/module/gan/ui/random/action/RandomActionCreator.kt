package com.huyingbao.module.gan.ui.random.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.gan.action.GanApi
import com.huyingbao.module.gan.action.GanConstants
import javax.inject.Inject


/**
 * rxAction创建发送管理类
 *
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class RandomActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val ganApi: GanApi
) : RxActionCreator(rxDispatcher, rxActionManager), RandomAction {
    override fun getDataList(category: String, count: Int, page: Int) {
        val rxAction = newRxAction(RandomAction.GET_DATA_LIST,
                GanConstants.Key.COUNT, count,
                GanConstants.Key.PAGE, page)
        postHttpAction(rxAction, ganApi.getDataList(category, count, page))
    }
}
