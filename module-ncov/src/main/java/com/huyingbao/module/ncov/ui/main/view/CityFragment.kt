package com.huyingbao.module.ncov.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.ncov.R
import com.huyingbao.module.ncov.ui.main.store.MainStore

/**
 * 城市数据
 */
class CityFragment : BaseFluxFragment<MainStore>() {
    companion object {
        fun newInstance() = CityFragment()
    }

    override fun getLayoutId() = R.layout.ncov_fragment_city

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
