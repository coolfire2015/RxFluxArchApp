package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
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

    override fun onSupportNavigateUp()=findNavController(getFragmentContainerId()).navigateUp()

    /**
     * [Toolbar]Menu点击事件，拦截返回按钮，如果Fragment回退栈不为空，先弹出Fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                findNavController(getFragmentContainerId()).navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
