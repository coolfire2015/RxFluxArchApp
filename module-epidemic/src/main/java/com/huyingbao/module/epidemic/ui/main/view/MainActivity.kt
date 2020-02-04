package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.epidemic.ui.main.store.MainStore

class MainActivity : BaseFluxFragActivity<MainStore>() {
    override fun createFragment(): Fragment? {
        return MainFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
