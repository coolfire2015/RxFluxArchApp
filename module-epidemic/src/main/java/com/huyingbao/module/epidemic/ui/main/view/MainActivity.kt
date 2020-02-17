package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxNavActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

@Route(path = CommonAppConstants.Router.EpidemicMainActivity)
class MainActivity : BaseFluxNavActivity<MainStore>() {
    override fun getGraphId() = R.navigation.epidemic_navigation_main

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
