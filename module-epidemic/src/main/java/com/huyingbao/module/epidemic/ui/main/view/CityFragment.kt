package com.huyingbao.module.epidemic.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.epidemic.R
import com.huyingbao.module.epidemic.ui.main.store.MainStore

/**
 * 城市数据
 */
class CityFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = CityFragment()
    }

    override fun getLayoutId() = R.layout.epidemic_fragment_city

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
