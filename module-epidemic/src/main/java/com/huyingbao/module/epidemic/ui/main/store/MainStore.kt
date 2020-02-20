package com.huyingbao.module.epidemic.ui.main.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.model.AreaProvince
import com.huyingbao.module.epidemic.ui.main.model.DingResponse
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    /**
     * 省数据
     */
    val provinceLiveData by lazy {
        MutableLiveData<ArrayList<AreaProvince>>()
    }

    override fun onCleared() {
        super.onCleared()
        provinceLiveData.value = null
    }

    @Subscribe(tags = [MainAction.GET_DING_DATA])
    fun onGetDingData(rxAction: RxAction) {
        provinceLiveData.value = rxAction.getResponse<DingResponse<ArrayList<AreaProvince>>>()?.results
    }
}

