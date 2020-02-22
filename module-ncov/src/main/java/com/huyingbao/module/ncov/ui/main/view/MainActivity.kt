package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.flux.activity.BaseFluxNavActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.store.MainStore
import org.jetbrains.anko.find

@Route(path = CommonAppConstants.Router.NcovMainActivity)
class MainActivity : BaseFluxNavActivity<MainStore>() {
    override fun getGraphId() = R.navigation.ncov_navigation_main

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
