package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.activity.BaseFluxNavActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.action.MainAction
import com.huyingbao.module.epidemic.ui.main.action.MainActionCreator
import com.huyingbao.module.epidemic.ui.main.store.MainStore
import kotlinx.android.synthetic.main.epidemic_activity_main.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@Route(path = CommonAppConstants.Router.EpidemicMainActivity)
class MainActivity : BaseFluxNavActivity<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator

    override fun getLayoutId() = R.layout.epidemic_activity_main

    override fun getFragmentContainerId() = R.id.main_fragment_container

    override fun getGraphId() = R.navigation.epidemic_navigation_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        //设置刷新监听器,刷新获取数据
        main_rfl_content?.setOnRefreshListener {
            mainActionCreator.getDingData()
        }
    }

    override fun onResume() {
        super.onResume()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的 Activity 重建，不需要重新获取数据
        if (rxStore?.provinceLiveData?.value == null) {
            main_rfl_content?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[RxLoading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [MainAction.GET_DING_DATA], sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            main_rfl_content?.finishRefresh()
        }
    }

    /**
     * 接收[RxError]，粘性
     */
    @Subscribe(tags = [MainAction.GET_DING_DATA], sticky = true)
    fun onRxError(rxError: RxError) {
        showCommonError(this, rxError)
    }
}
