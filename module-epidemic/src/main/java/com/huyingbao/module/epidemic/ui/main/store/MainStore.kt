package com.huyingbao.module.epidemic.ui.main.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.model.AreaProvince
import com.huyingbao.module.epidemic.ui.main.model.DingResponse
import com.huyingbao.module.epidemic.ui.main.model.OverAll
import com.huyingbao.module.epidemic.ui.main.model.Rumor
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    val overAllLiveData by lazy {
        MutableLiveData<OverAll>()
    }
    val areaLiveData by lazy {
        MutableLiveData<ArrayList<AreaProvince>>()
    }
    val rumorLiveData by lazy {
        MutableLiveData<ArrayList<Rumor>>()
    }

    override fun onCleared() {
        super.onCleared()
        areaLiveData.value = null
    }

    @Subscribe(tags = [MainAction.GET_OVER_ALL])
    fun onGetOverAll(rxAction: RxAction) {
        overAllLiveData.value = rxAction.getResponse<DingResponse<ArrayList<OverAll>>>()?.results?.get(0)
    }

    @Subscribe(tags = [MainAction.GET_AREA_DATA])
    fun onGetAreaData(rxAction: RxAction) {
        areaLiveData.value = rxAction.getResponse<DingResponse<ArrayList<AreaProvince>>>()?.results
    }

    @Subscribe(tags = [MainAction.GET_AREA_DATA])
    fun onGetRumors(rxAction: RxAction) {
        rumorLiveData.value = rxAction.getResponse<DingResponse<ArrayList<Rumor>>>()?.results
    }
}

