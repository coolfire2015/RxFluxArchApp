package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.epidemic.ui.main.store.MainStore

@Route(path = CommonAppConstants.Router.EpidemicMainActivity)
class MainActivity : BaseFluxFragActivity<MainStore>() {
    override fun createFragment(): Fragment? {
        return MainFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
